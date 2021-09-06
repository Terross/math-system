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
          <div>
            Постройте граф, удовлетворяющий следующим свойствам:
          </div>
          <div
          v-for="(plugin, i) in plugins"
          :key="i"
          v-if="selected[i] && values[i]">
            {{plugin.description + " " + values[i]}}
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
          <v-text-field
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
export default {
  name: "pluginList",
  components: {graphEditor},
  data() {
    return {
      plugins: store.state.plugins,
      values: [],
      selected: [],
      taskName: '',
      taskCategory: '',
      graph: false,
      valid: true
    }
  },
  props: {
    graphVertexies: Array,
    graphEdges: Array
  },
  watch: {
    graph(value) {
      this.$parent.graphEditorVisible = value
    }
  },
  methods: {
    saveTask() {
      if (this.$refs.form.validate()) {
        const algAnswerList = []
        for (let i = 0; i < this.plugins.length; i++) {
          if (this.selected[i]) {
            algAnswerList.push({
              "algorithm" : this.plugins[i],
              "answer" : this.values[i]
            })
          }
        }
        let graph = null;
        if (this.graph) {
          graph = {
            "vertexCount" : this.graphVertexies.length,
            "edgeCount" : this.graphEdges.length,
            "vertexes" : this.graphVertexies
          }
        }

        const data = {
          "name" : this.taskName,
          "category" : this.taskCategory,
          "algAnswerList" : algAnswerList,
          "graph" : graph
        }
        console.log(data)
        this.$http.post("task/addNewTask", data).then(response => {
          console.log(response)
        })
      }

    }
  }
}
</script>

<style scoped>

</style>