<template>
  <v-container>
    <v-col>
      <v-card
          class="mx-auto"
          min-width="344"
      >
        <v-card-text>
          <p class="text-h4 text--primary">
            Описание задачи
          </p>
          <div v-if="">
            Постройте граф, удовлетворяющий следующим свойствам:
          </div>
          <div
              v-for="(plugin, i) in plugins"
              :key="i"
              >
            <div v-if="selected[i] && values[i] && plugin.algorithmType === 'CHARACTERISTIC'">
              {{plugin.description + " " + values[i]}}
            </div>
            <div v-if="selected[i] && plugin.algorithmType === 'PROPERTY'">
              {{plugin.description}}
            </div>
          </div>
          <v-switch
              v-model="graph"
              label="Граф заранее построен"></v-switch>
          <v-form
          ref = "form"
          v-model = "valid"
          lazy-validation>
            <v-text-field
                v-model="taskName"
                label="Название задачи"
                :rules="[v => !!v || 'Требуется название задачи']"
                required
            ></v-text-field>
            <v-text-field
                v-model="taskCategory"
                label="Категория задачи"
                :rules="[v => !!v || 'Требуется категория задачи']"
                required
            ></v-text-field>
          </v-form>

        </v-card-text>
        <v-card-actions>
          <v-btn
              color="indigo lighten-1"
              @click="saveTask"
              dark
          >
            Создать задачу
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
    <v-col v-if="$store.state.plugins.plugins.length > 0"
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
          <v-text-field v-if="plugin.algorithmType === 'CHARACTERISTIC'"
              v-model="values[i]"
              label="Требуемое значение"
              required
          ></v-text-field>
          <v-switch
              v-model="selected[i]"
              label="Добавить плагин в задачу"></v-switch>
        </v-card-text>
      </v-card>
    </v-col>
  </v-container>

</template>

<script>

import graphEditor from "../cylc/graphEditor.vue";
import store from "../../store/store.js";
import {mapActions, mapMutations} from "vuex";
export default {
  name: "pluginList",
  components: {graphEditor},
  data() {
    return {
      values: [],
      selected: [],
      taskName: '',
      taskCategory: '',
      graph: this.$store.state.constructorGraph.graphPresent,
      valid: true
    }
  },
  watch: {
    graph() {
      this['constructorGraph/graphPresentMutation']()
    }
  },
  computed: {
    plugins() {
      return this.$store.state.plugins.plugins
    },
    graphData() {
      return this.$store.state.constructorGraph
    }
  },
  methods: {
    ...mapActions(['tasks/addTaskAction']),
    ...mapMutations(['constructorGraph/graphPresentMutation']),
    saveTask() {
      if (this.$refs.form.validate()) {
        const algAnswerList = []

        for (let i = 0; i < this.plugins.length; i++) {
          if (this.selected[i]) {
            if (this.plugins[i].algorithmType === 'CHARACTERISTIC') {
              algAnswerList.push({
                "type" : 'characteristic',
                "algorithm" : this.plugins[i],
                "answer" : this.values[i]
              })
            } else {
              algAnswerList.push({
                "type" : 'property',
                "algorithm" : this.plugins[i],
                "answer" : true
              })
            }
          }
        }

        let graph = null;

        if (this.graph) {
          graph = {
            "vertexCount" : this.graphData.constructorGraph.length,
            "edgeCount" : 2,
            "vertexes" : this.graphData.constructorGraph,
            "graphType" : this.$store.state.constructorGraph.direct ? "DIRECTED" : "UNDIRECTED"
          }
        }

        const data = {
          "name" : this.taskName,
          "category" : this.taskCategory,
          "algAnswerList" : algAnswerList,
          "graph" : graph
        }

        //this['tasks/addTaskAction'](data)
        this.$http.post("task/", data).then(response => {
          console.log(response)
        })
      }

    }
  }
}
</script>

<style scoped>

</style>