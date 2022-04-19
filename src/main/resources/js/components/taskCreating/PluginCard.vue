<template>
  <v-card
      class="mx-auto"
      min-width="320"
  >
    <v-card-title>{{plugin.name}}</v-card-title>
    <v-card-subtitle>{{plugin.description}}</v-card-subtitle>
    <v-card-text v-if="task.graphIsPresent">
      <v-btn
          color="primary"
          @click="getPluginResult"
          text
          small
          dark
      >
        <v-icon  dark>visibility</v-icon>
      </v-btn>
      Результат плагина для графа: {{result}}
    </v-card-text>
    <v-card-actions>
      <v-col>
        <v-select
            v-if="plugin.pluginType === 'CHARACTERISTIC'"
            :items="['равно ', 'меньше ', 'больше ']"
            v-model="valueType"
            :rules="[v => !!v || 'Требуется тип графа']"
            :disabled="selected"
            required
        ></v-select>
        <v-text-field v-if="plugin.pluginType === 'CHARACTERISTIC'"
                      v-model="value"
                      label="Требуемое значение"
                      required
        ></v-text-field>

        <v-switch v-if="plugin.pluginType !== 'CHARACTERISTIC'"
                  v-model="value"
                  :label="`Требование: ${value? ' выполняется' :  'невыполняется'}`"></v-switch>
        <v-switch
            v-model="selected"
            label="Добавить плагин в задачу"></v-switch>
      </v-col>
    </v-card-actions>
  </v-card>
</template>

<script>
import {mapMutations} from "vuex";
import {HTTP} from "../../axios/http-common.js";

export default {
  name: "PluginCard",
  props: {
    plugin: Object
  },
  data() {
    return {
      value: '',
      selected: false,
      result: '',
      valueType: 'равно '
    }
  },
  watch: {
    value: 'editValue',
    selected: 'editSelected',
    valueType: 'editValue'
  },
  computed: {
    graph() {
      return this.$store.state.currentGraph
    },
    task() {
      return this.$store.state.tasks.currentTask
    },
    token() {
      return this.$store.state.profile.profile.jwt
    }
  },
  methods: {
    ...mapMutations([
        'tasks/addPluginToCurrentTask',
        'tasks/removePluginFormCurrentTask'
    ]),
    getPluginResult() {
      const data = {
        "vertexCount" : this.graph.vertexCount,
        "edgeCount" : this.graph.edgeCount,
        "vertexList" : this.graph.vertexList,
        "edgeList": this.graph.edgeList,
        "directType" : this.task.graphDirect ? "DIRECTED" : "UNDIRECTED"
      }
      this.$http
          .post(`/api/v1/all/plugin/chech-plugin/${this.plugin.id}`, data, {
            headers: {
              'Authorization' : "Bearer " + this.token
            }
          })
          .then(response => {
            this.result = response.bodyText
          })
          .catch(e => {
            console.log(e)
          })
    },
    editSelected() {
      const value = this.plugin.pluginType === 'CHARACTERISTIC'
          ? this.valueType.toString()+this.value.toString()
          : (this.value ? "верно" : "неверно")

      if (this.selected && this.value) {
        this['tasks/addPluginToCurrentTask']({
          plugin: this.plugin,
          value: value
        })
      } else {
        this['tasks/removePluginFormCurrentTask']({
          plugin: this.plugin,
          value: value
        })
      }
    },
    editValue() {
      const value = this.plugin.pluginType === 'CHARACTERISTIC'
          ? this.valueType.toString()+this.value.toString()
          : (this.value ? "верно" : "неверно")
      if (this.selected) {
        this['tasks/addPluginToCurrentTask']({
          plugin: this.plugin,
          value: value
        })
      }
      if (!this.value) {
        this['tasks/removePluginFormCurrentTask']({
          plugin: this.plugin,
          value: value
        })
      }
    }
  }
}
</script>

<style scoped>

</style>