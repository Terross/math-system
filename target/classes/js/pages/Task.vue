<template>
  <v-container fluid>
      <v-row>
        <v-col style="padding-bottom: 24px">
          <graph-editor ></graph-editor>
        </v-col>
        <v-col>
          <v-row>
            <v-col>
              <v-card
                  class="mx-auto"
                  min-width="344"
              >
                <v-card-text>
                  <p class="text-h4 text--primary">
                    {{ task.name }}
                  </p>
                  <div>
                    {{task.category}}
                  </div>
                  <div>
                    Постройте граф, удовлетворяющий следующим свойствам:
                  </div>
                  <div
                      v-for="(plugin, i) in task.algAnswerList"
                      :key="i">
                    {{plugin.algorithm.description + " " + plugin.answer}}
                  </div>
                </v-card-text>

                <v-card-actions>
                  <v-btn
                      color="indigo lighten-1"
                      @click="verify"
                      dark
                  >
                    Отправить решение
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
          <v-row>
            <v-col v-if="answer && result === 'wrong'">
              <v-card class="mx-auto"
                      min-width="344"
                      color="red lighten-4"
              >
                <v-card-text>
                  <p class="text-h4">
                    Решение неверно, повторите попытку.
                  </p>
                </v-card-text>
              </v-card>
            </v-col>
            <v-col v-if="answer && result === 'right'">
              <v-card class="mx-auto"
                      min-width="344"
                      color="green lighten-4"
              >
                <v-card-text>
                  <p class="text-h4">
                    Задача решена верно!!!
                  </p>
                </v-card-text>
              </v-card>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
  </v-container>
</template>

<script>
import graphEditor from "../components/cylc/graphEditor.vue";
import {mapGetters} from "vuex";
import tasks from "../api/tasks";
export default {
  name: "Task",
  data() {
    return {
      graph: null,
      taskData: this.task,
      graphVertexies: null,
      edgeCount: 0,
      answer: false,
      result: "wrong"
      //graphExist: this.task.graph !== null
    }
  },
  components: {
    graphEditor
  },
  computed: {
    task() {
      let id = this.$route.params.id
      return this.$store.getters.findTaskById(id)
    }
  },
  methods: {
    verify() {
      const graph = {
        "vertexCount" : this.graphVertexies.length,
        "edgeCount" : this. edgeCount + (this.task.graph !== null ? this.task.graph.edgeCount : 0),
        "vertexes" : this.graphVertexies
      }
      console.log(graph)
      this.$http.post(`/task/verifyTask/${this.$route.params.id}`, graph).then(response => {
        this.answer = true
        console.log(response.data)
        this.result = response.data ? 'right' : 'wrong'
      })
    }

  },
  mounted() {
    this.graphVertexies = this.task.graph !== null ? this.task.graph.vertexes : []
  }
}
</script>

<style scoped>

</style>