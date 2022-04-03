<template>
  <v-container>
    <NativePluginForm
        :dialog="dialog"
        v-on:close-dialog="dialog=false"
    />
      <v-row>
        <v-col>
          <v-card
              class="ma-auto"
          >
            <v-card-text class="ma-2">
              <p class="text-h4 text--primary">
                Загрузите плагин
              </p>
              <div class="text--primary">
                Загрузите jar файл.
                Описание плагина должно описывать то, как плагин будет выглядет в задаче.
              </div>
              <v-form
                  ref = "form"
                  class="ma-2"
                  v-model = "valid"
                  lazy-validation>
                <v-row>
                  <v-col>
                    <v-text-field
                        v-model="pluginName"
                        label="Название плагина"
                        :rules="[v => !!v || 'Требуется название плагина']"
                        required
                    ></v-text-field>
                    <v-text-field
                        v-model="pluginDescription"
                        label="Описание плагина"
                        :rules="[v => !!v || 'Требуется описание плагина']"
                        required
                    ></v-text-field>
                    <v-select
                        :items="['Ориентированный', 'Неориентированный', 'Любой']"
                        label="Тип графа"
                        v-model="graphType"
                        :rules="[v => !!v || 'Требуется тип графа']"
                        required
                    ></v-select>
                  </v-col>

                </v-row>
                <v-row>
                  <v-file-input
                      v-model="files"
                      color="primary"
                      counter
                      class="ma-4"
                      label="Jar файл"
                      placeholder="Загрузите файл"
                      prepend-icon="mdi-paperclip"
                      outlined
                      :rules="[
                   v => !!v || 'File is required',
                   v => (v && v.size > 0) || 'File is required',
                ]"
                      :show-size="1000"
                  >
                    <template v-slot:selection="{ index, text }">
                      <v-chip
                          v-if="index < 2"
                          color="primary"
                          label
                          small
                      >
                        {{ text }}
                      </v-chip>
                    </template>
                  </v-file-input>
                </v-row>
              </v-form>
              <plugin-alerts
                  :alert-type="alertType"
                  :alert-message="alertMessage"/>
            </v-card-text>
            <v-card-actions>
              <v-col>
                <v-row justify="start">
                  <v-btn
                      color="primary"
                      @click="addPlugin()"
                      class="ma-4"
                      dark>
                    Добавить плагин
                  </v-btn>
                  <v-btn
                      v-if="author==='dima38091@gmail.com'"
                      color="primary"
                      class="ma-4"

                      @click="addNativePlugin()"
                      dark>
                    Добавить нативный плагин
                  </v-btn>
                </v-row>
                <v-row>
                  <v-text-field
                      v-model="searchField"
                      label="Поиск по имени плагина"
                      class="ma-4"

                  ></v-text-field>
                </v-row>

              </v-col>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
      <v-row justify="start">
        <v-col
            v-for="(plugin, i) in plugins"
            :key="i"
            v-if="plugins.length > 0">
          <PluginCard :plugin="plugin" v-if="plugin.name.includes(searchField) || searchField.length === 0 "/>
        </v-col>
      </v-row>
  </v-container>
</template>

<script>
import {HTTP} from "../../axios/http-common";
import { mapMutations } from "vuex";
import PluginAlerts from "./PluginAlerts.vue";
import PluginCard from "./PluginCard.vue";
import NativePluginForm from "./NativePluginForm.vue";

export default {
  name: "PluginList",
  components: {NativePluginForm, PluginCard, PluginAlerts},
  data() {
    return {
      values: [],
      selected: [],
      pluginName: '',
      pluginDescription: '',
      pluginType: '',
      graphType: '',
      files: [],
      valid: true,
      alertType: '',
      alertMessage: '',
      jarName: '',
      dialog: false,
      searchField: '',
      searchMode: false
    }
  },
  computed: {
    plugins() {
      return this.$store.state.plugins.plugins
    },
    token() {
      return this.$store.state.profile.profile.jwt
    },
    currentRole() {
      return this.$store.state.profile.profile.role
    },
    author() {
      return this.$store.state.profile.profile.email
    }
  },
  methods: {
    ...mapMutations(['plugins/addPluginMutation', 'plugins/removePluginMutation']),
    addNativePlugin() {
      this.dialog = true
    },
    addPlugin() {
      if (this.$refs.form.validate()) {
        let file = this.files
        if (file.size > 131000) {
          this.alertType = 'error'
          this.alertMessage = 'Файл не должен превышать 128кб'
        } else {
          const gt = this.graphType === 'Ориентированный' ? "DIRECTED" :
              this.graphType === "Неориентированный" ? "UNDIRECTED": "COMMON"
          let formData = new FormData();
          formData.append("file", file)
          formData.append("name", this.pluginName)
          formData.append("description", this.pluginDescription)
          formData.append("pluginType", this.pluginType === 'Характеристика'
              ? 'CHARACTERISTIC' : 'PROPERTY')
          formData.append("graphType", gt)
          formData.append("author", this.author)

          console.log(formData)

          this.$http
            .post('/api/v1/all/plugin/external-plugin', formData, {
              headers: {
                'Authorization' : "Bearer " + this.token
              }
            })
            .then(response => {
              this['plugins/addPluginMutation'](response.data)
              this.alertMessage = 'Плагин успешно загружен'
              this.alertType = 'success'
            }).catch(err => {
              this.alertType = 'error'
              const data = err.response.data
              switch (data.code) {
                case "PLUGIN_INTERNAL_ERROR":
                  this.alertMessage = data.message
                  break
                case "PLUGIN_ALREADY_EXIST":
                  this.alertMessage = 'Плагин уже существует'
                  break
                case "PLUGIN_JAR_ALREADY_EXIST":
                  this.alertMessage = 'Jar уже существует'
                  break
                case "PLUGIN_JAR_NOT_FOUND":
                  this.alertMessage = 'Jar не найдет'
                  break
                case "PLUGIN_TIME_LIMIT":
                  this.alertMessage = 'Тест по времени не пройдет'
                  break
                case "PLUGIN_CLASS_NOT_FOUND":
                  break
              }
            })
        }
      }
    },
    search() {

    }
  }
}
</script>
<style scoped>

</style>