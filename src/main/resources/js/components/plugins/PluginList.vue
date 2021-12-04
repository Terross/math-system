<template>
  <v-container>
    <v-col>
      <v-card
          class="mx-auto"
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
                  </v-col>
                  <v-col>
                    <v-select
                        :items="['Свойство', 'Характеристика']"
                        label="Тип плагина"
                        v-model="pluginType"
                        :rules="[v => !!v || 'Требуется тип плагина']"
                        required
                    ></v-select>
                    <v-select
                        :items="['Ориентированный', 'Неориентированный']"
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
            :plugin-name="jarName"
            :java-error="javaError"/>
        </v-card-text>
        <v-card-actions>
          <v-btn
              color="primary"
              class="mx-4 mb-4"
              @click="addPlugin()"
              dark>
            Добавить плагин
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
    <v-col
        v-for="(plugin, i) in plugins"
        :key="i"
        cols="12"
        v-if="plugins.length > 0">
      <PluginCard :plugin="plugin"/>
    </v-col>
  </v-container>
</template>

<script>
import store from "../../store/store";
import {mapActions, mapMutations} from "vuex";
import PluginAlerts from "./PluginAlerts.vue";
import PluginCard from "./PluginCard.vue";

export default {
  name: "PluginList",
  components: {PluginCard, PluginAlerts},
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
      javaError: '',
      jarName: ''
    }
  },
  computed: {
    plugins() {
      return store.state.plugins.plugins
    }
  },
  methods: {
    ...mapActions(['plugins/addPluginAction']),
    ...mapMutations(['plugins/addPluginMutation', 'plugins/removePluginMutation']),
    addPlugin() {
      if (this.$refs.form.validate()) {
        let file = this.files
        if (file.size > 131000) {
          console.log(123)
          this.alertType = 'errorSize'
        } else {

          let formData = new FormData();
          formData.append("file", file)
          this.jarName = file.name
          console.log(file.size)
          formData.append("name", this.pluginName)
          formData.append("description", this.pluginDescription)
          formData.append("algType", this.pluginType === 'Характеристика'
              ? 'CHARACTERISTIC' : 'PROPERTY')
          formData.append("graphType", this.graphType === 'Ориентированный'
              ? 'DIRECTED' : 'UNDIRECTED')

          this.$http.post('/plugin/api', formData).then(response => {
            this['plugins/addPluginMutation'](response.data)
            this.alertType = 'success'
          }, err => {
            if (err.data === null) {
              this.alertType = 'somethingWrong'
              this.javaError = err.bodyText
            } else {
              if (err.data.message === 'PluginAlreadyExists') {
                this.alertType = 'errorExist'
              }
              if (err.data.message === 'Wrong jar file') {
                this.alertType = 'errorInterface'
              }
              if (err.data.message === 'Class wasnt found in jar') {
                this.alertType = 'errorJar'
              }
              if (err.data.message === 'The plugin takes too long to execute') {
                this.alertType = 'errorTime'
              }
            }
          })
        }

      }
    }
  }
}
</script>
<style scoped>

</style>