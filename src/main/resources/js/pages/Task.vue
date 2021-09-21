<template>
  <v-container fluid>
      <v-row>
        <v-col>
          <graph-editor :config-elements="graph"></graph-editor>
        </v-col>
        <v-col>
          <v-row>
            <element-redactor>
            </element-redactor>
            <v-container>
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
                      Постройте {{this.dataForGraph.graphType === "DIRECTED"
                        ? 'ориентированный': 'неориентированный'}} граф, удовлетворяющий следующим свойствам:
                    </div>
                    <div
                        v-for="(plugin, i) in task.algAnswerList"
                        :key="i">
                      {{plugin.algorithm.description + " " +
                    ((plugin.type === 'characteristic') ? plugin.answer :
                    (plugin.answer?'выполнено':'невыполнено'))}}
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
            </v-container>
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
import {mapGetters, mapMutations} from "vuex";
import ElementRedactor from "../components/taskCreating/elementRedactor.vue";

export default {
  name: "Task",
  data() {
    return {
      answer: false,
      result: "wrong"
    }
  },
  components: {
    ElementRedactor,
    graphEditor
  },
  computed: {
    task() {
      let id = this.$route.params.id
      return this.$store.getters["tasks/findTaskById"](id)
    },
    graph() {
      let id = this.$route.params.id
      return this.$store.getters["constructorGraph/cytoscapeConfigElements"]
    },
    dataForGraph() {
      let id = this.$route.params.id
      return this.$store.getters["tasks/findTaskById"](id)
    },
    st() {
      return this.$store.state.constructorGraph
    }
  },
  methods: {
    ...mapMutations([
        'constructorGraph/initMutation',
        'constructorGraph/cleanGraphMutation'
    ]),
    ...mapGetters('tasks', {
      taskGetter: 'findTaskById',
      graphDataGetter: 'findGraphDataByTaskId'
    }),
    verify() {
      const graphData = this.$store.state.constructorGraph
      const graph = {
        "vertexCount" : graphData.vertexCount,
        "edgeCount" : graphData.edgeCount,
        "vertexes" : graphData.constructorGraph,
        "graphType" : this.$store.state.constructorGraph.direct ? "DIRECTED" : "UNDIRECTED"
      }

      this.$http.post(`/task/verifyTask/${this.$route.params.id}`, graph).then(response => {
        this.answer = true
        this.result = response.data ? 'right' : 'wrong'
      })
    }
  },
  beforeMount() {
    const graph = this.task.graph
    const permission = this.task.taskPermission
    console.log(graph)
    const graphData = graph !== null
        ? {
          'edgeCount' : graph.edgeCount,
          'vertexCount': graph.vertexCount,
          'vertexes' : graph.vertexes,
          'permission' : permission,
          'direct' : graph.direct
        }
        : {
          'edgeCount' : 0,
          'vertexCount': 0,
          'vertexes' : [],
          'permission' : permission,
          'direct' : this.dataForGraph.graphType === 'DIRECTED'
        }
    this['constructorGraph/initMutation'] (graphData)
  }
}
</script>

<style scoped>

</style>