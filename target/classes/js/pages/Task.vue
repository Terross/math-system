<template>
  <v-container fluid>
      <v-row>
        <v-col>
          <graphProjection-editor :config-elements="graphProjection">
          </graphProjection-editor>
        </v-col>
        <v-col>
          <v-container>
            <v-col>
              <v-card
                  class="mx-auto"

              >
                <v-card-text>
                  <v-row>
                    <v-col>
                      <p class="text-h4 text--primary">
                        {{ task.name }}
                      </p>
                      <div>
                        {{task.category}}
                      </div>
                      <span  style="white-space: pre-line">
                      {{task.taskDescription}}
                      </span>
                    </v-col>


                  </v-row>

                </v-card-text>

                <v-card-actions>
                  <v-btn
                      color="primary"
                      @click="verify"
                      dark
                  >
                    Отправить решение
                  </v-btn>
                </v-card-actions>
              </v-card>



              <v-alert
                  type="success"
                  v-model="rightAnswer"
                  class="my-2"
                  text
                  outlined
                  dismissible>
                Задача решена верно!
              </v-alert>
              <v-alert
                  type="error"
                  v-model="wrongAnswer"
                  class="my-2"
                  text
                  outlined
                  dismissible>
                Вы ошиблись, попробуйте снова!
              </v-alert>
            </v-col>
          </v-container>



        </v-col>
      </v-row>
  </v-container>
</template>

<script>
import graphEditor from "../components/cylc/graphEditor.vue";
import {mapGetters, mapMutations} from "vuex";

export default {
  name: "Task",
  data() {
    return {
      result: "wrong",
      rightAnswer: false,
      wrongAnswer: false
    }
  },
  components: {
    graphEditor
  },
  computed: {
    task() {
      let id = this.$route.params.id
      return this.$store.getters["tasks/findTaskById"](id)
    },
    graphProjection() {
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
      const graphProjection = {
        "vertexCount" : graphData.vertexCount,
        "edgeCount" : graphData.edgeCount,
        "vertexes" : graphData.constructorGraph,
        "graphType" : this.$store.state.constructorGraph.direct ? "DIRECTED" : "UNDIRECTED"
      }

      this.$http.post(`/task/verifyTask/${this.$route.params.id}`, graphProjection).then(response => {
        let answer = response.data

        if (answer) {
          this.rightAnswer = true
          this.wrongAnswer = false
        } else {
          this.rightAnswer = false
          this.wrongAnswer = true
        }

      })
    }
  },
  beforeMount() {
    const graphProjection = this.task.graphProjection
    const permission = this.task.taskPermission
    console.log(graphProjection)
    const graphData = graphProjection !== null
        ? {
          'edgeCount' : graphProjection.edgeCount,
          'vertexCount': graphProjection.vertexCount,
          'vertexes' : graphProjection.vertexes,
          'permission' : permission,
          'direct' : graphProjection.graphType === 'DIRECTED'
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