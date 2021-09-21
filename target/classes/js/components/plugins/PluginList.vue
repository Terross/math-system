<template>
  <v-container>
    <v-col>
      <v-card
          class="mx-auto"
          min-width="344"
      >
        <v-card-text>
          <p class="text-h4 text--primary">
            Загрузите плагин
          </p>
          <div class="text--primary">
            Загрузите jar файл. Название плагина должно совпадать с названием jar файла.
            Описание плагина должно описывать то, что плагин будет возвращать.
          </div>

          <v-form
              ref = "form"
              v-model = "valid"
              lazy-validation>
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
                :items="['Свойство', 'Характеристика']"
                label="Тип плагина"
                v-model="pluginType"
                :rules="[v => !!v || 'Требуется тип плагина']"
                required
            ></v-select>
            <v-file-input
                v-model="files"
                color="indigo lighten-1"
                counter
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
                    color="indigo lighten-1"
                    dark
                    label
                    small
                >
                  {{ text }}
                </v-chip>
              </template>
            </v-file-input>
          </v-form>

          <v-alert
              type="success"
              v-model="successAlert"
              text
              outlined
              dismissible>
            Плагин успешно загружен!
          </v-alert>
          <v-alert
              type="error"
              v-model="errorNameAlert"
              text
              outlined
              dismissible>
            Имя плагина не совпадает с именем файла!
          </v-alert>
          <v-alert
              type="error"
              v-model="errorInterfaceAlert"
              text
              outlined
              dismissible>
            Класс в jar реализует другой тип плагина!
          </v-alert>
          <v-alert
              type="error"
                   v-model="errorJarAlert"
                   text
                   outlined
                   dismissible>
            Класса именем {{pluginName}} не обнаружено в jar!
          </v-alert>
          <v-alert
              type="error"
              v-model="alreadyExistAlert"
              text
              outlined
              dismissible>
            Плагин уже добавлен!
          </v-alert>
        </v-card-text>
        <v-card-actions>
          <v-btn
              color="indigo lighten-1"
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
      <v-card
          class="mx-auto"
          min-width="344"
      >
        <v-card-text>
          <p class="text-h4 text--primary">
            {{plugin.name}}
          </p>
          <p class="text-h6 text--primary">
            {{plugin.algorithmType === 'CHARACTERISTIC' ?  'Характеристика' : 'Свойство'}}
          </p>
          <div class="text--primary">
            {{plugin.description}}
          </div>
        </v-card-text>
        <v-card-actions>
          <v-btn
              color="red lighten-1"
              @click="removePlugin(plugin.id)"
              dark>
            Удалить плагин
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-container>
</template>

<script>
import store from "../../store/store";
import {mapActions, mapMutations} from "vuex";

export default {
  name: "PluginList",
  data() {
    return {
      values: [],
      selected: [],
      pluginName: '',
      pluginDescription: '',
      pluginType: '',
      files: [],
      valid: true,
      successAlert: false,
      errorNameAlert: false,
      errorInterfaceAlert: false,
      errorJarAlert: false,
      alreadyExistAlert: false

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
    removePlugin(id) {
      this.$http.delete(`/plugin/api/${id}`).then(response => {
        if (response.ok) {
          this['plugins/removePluginMutation'](this.plugins.filter(item => item.id === id)[0])
        }
      })
    },
    addPlugin() {

      if (this.$refs.form.validate()) {
        let file = this.files
        if (this.pluginName + '.jar' === file.name) {

          console.log(this.pluginName + '.jar')
          console.log(file.name)
          console.log()
          let formData = new FormData();
          formData.append("file", file)
          formData.append("name", this.pluginName)
          formData.append("description", this.pluginDescription)
          formData.append("algType", this.pluginType === 'Характеристика'
              ? 'CHARACTERISTIC' : 'PROPERTY')


            this.$http.post('/plugin/api', formData).then(response => {

              this['plugins/addPluginMutation'](response.data)
              this.alreadyExistAlert = false
              this.errorJarAlert = false
              this.errorNameAlert = false
              this.errorInterfaceAlert = false
              this.successAlert = true
            }, err => {
              if (err.data.message === 'PluginAlreadyExists') {
                this.alreadyExistAlert = true
                this.errorJarAlert = false
                this.errorNameAlert = false
                this.errorInterfaceAlert = false
                this.successAlert = false
              }
              if (err.data.message === 'Wrong jar file') {
                this.alreadyExistAlert = false
                this.errorJarAlert = false
                this.errorNameAlert = false
                this.errorInterfaceAlert = true
                this.successAlert = false
              }
              if (err.data.message === 'Class wasn\'t found in jar') {
                this.alreadyExistAlert = false
                this.errorJarAlert = true
                this.errorNameAlert = false
                this.errorJarAlert = false
                this.successAlert = false
              }
            })

        } else {
          this.alreadyExistAlert = false
          this.errorJarAlert = false
          this.errorNameAlert = true
          this.errorJarAlert = false
          this.successAlert = false
        }

      }
    }
  }
}
</script>

<style scoped>

</style>