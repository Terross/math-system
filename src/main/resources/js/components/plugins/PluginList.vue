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
          <v-text-field
              v-model="pluginName"
              label="Название плагина"
              required
          ></v-text-field>
          <v-text-field
              v-model="pluginDescription"
              label="Описание плагина"
              required
          ></v-text-field>
          <v-file-input
              v-model="files"
              color="indigo lighten-1"
              counter
              label="Jar файл"
              multiple
              placeholder="Загрузите файл"
              prepend-icon="mdi-paperclip"
              outlined
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
        cols="12">
      <v-card
          class="mx-auto"
          min-width="344"
      >
        <v-card-text>
          <p class="text-h4 text--primary">
            {{plugin.name}}
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
      files: []
    }
  },
  computed: {
    plugins() {
      return store.state.plugins
    }
  },
  methods: {
    ...mapActions(['addPluginAction']),
    ...mapMutations(['addPluginMutation', 'removePluginMutation']),
    removePlugin(id) {
      this.$http.delete(`/plugin/api/${id}`).then(response => {
        if (response.ok) {
          this.removePluginMutation(this.plugins.filter(item => item.id === id)[0])
        }
      })
    },
    addPlugin() {
      const data = {
        "name" : this.pluginName,
        "description" : this.pluginDescription,
        "file" : this.files[0]
      }

      let file = this.files[0]
      let formData = new FormData();
      formData.append("file", this.files[0])
      formData.append("name", this.pluginName)
      formData.append("description", this.pluginDescription)
      console.log(file)
      console.log(formData.get("file"))

      this.$http.post('/plugin/api', formData).then(response => {

        this.addPluginMutation(response.data)
      })
    }
  }
}
</script>

<style scoped>

</style>