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
            Постройте {{graphType? 'ориентированный' : 'неориентированный'}}   граф, удовлетворяющий следующим свойствам:
          </div>
          <div
              v-for="(plugin, i) in plugins"
              :key="i"
              >
            <div v-if="selected[i] && values[i] && plugin.algorithmType === 'CHARACTERISTIC'">
              {{plugin.description + " " + values[i]}}
            </div>
            <div v-if="selected[i] && plugin.algorithmType === 'PROPERTY'">
              {{plugin.description + " " + (values[i] ? 'выполнено' : 'невыполнено')}}
            </div>
          </div>

          <v-switch
              v-model="graph"
              label="Граф заранее построен"></v-switch>
          <v-row>
            <v-col>
              <p class="text-h7  text--primary">
                Данные о задаче:
              </p>
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
            </v-col>
            <v-col>
              <p class="text-h7  text--primary">
                Тип графа:
              </p>
              <v-btn-toggle
                  v-model="graphType"
                  tile
                  color="indigo lighten-1"
                  group
              >

                <v-btn
                    :value="true"
                    id="directed-type-graph"
                >
                  Ориентированный
                </v-btn>

                <v-btn
                    :value="false"
                    id="undirected-type-graph">
                  Неориентированный
                </v-btn>
              </v-btn-toggle>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <p class="text-h7  text--primary">
                Ограничения конструктора
              </p>
              <v-row >
                <v-switch
                    class="mx-2"
                    v-model="permission.edit"
                    label="Строительство"></v-switch>
                <v-switch
                    class="mx-2"
                    v-model="permission.color"
                    label="Цвета"></v-switch>
                <v-switch
                    class="mx-2"
                    v-model="permission.weight"
                    label="Вес ребер"></v-switch>
                <v-switch
                    class="mx-2"
                    v-model="permission.remove"
                    label="Удаление"></v-switch>
              </v-row>

            </v-col>

          </v-row>

        </v-card-text>
        <v-card-actions>
          <v-btn
              color="indigo lighten-1"
              @click="saveTask"
              dark
              class="ma-2"
          >
            Создать задачу
          </v-btn>
          <v-btn
              color="indigo lighten-1"
              @click="findPluginResult"
              dark
              class="ma-2"
              :disabled="!graphEditorVisible"
          >
            Узнать результаты плагинов
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
    <v-col>
      <v-row justify="start">
        <v-col v-if="$store.state.plugins.plugins.length > 0"
               v-for="(plugin, i) in plugins"
               :key="i"

        >
          <v-card
              class="mx-auto"
              min-width="250"
              min-height="262"

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
              <v-switch v-if="plugin.algorithmType !== 'CHARACTERISTIC'"
                        v-model="values[i]"
                        :label="`Требование: ${values[i]? ' выполняется' :  'невыполняется'}`"></v-switch>
              <v-switch
                  v-model="selected[i]"
                  label="Добавить плагин в задачу"></v-switch>
              <p class="text-h8 text--primary" v-if="answersPresent" >
                Результат плагина для текущего графа: {{typeof answers[i] == "undefined" ? '' : answers[i].answer}}
              </p>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
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
  props: {
    graphEditorVisible: Boolean
  },
  data() {
    return {
      values: [],
      selected: [],
      taskName: '',
      taskCategory: '',
      graph: this.$store.state.constructorGraph.graphPresent,
      graphType: this.$store.state.constructorGraph.direct,
      permission: this.$store.state.constructorGraph.permission,
      valid: true,
      pluginResult: false
    }
  },
  watch: {
    graph() {
      this['constructorGraph/graphPresentMutation']()
      this.pluginResult = false
    },
    graphType() {
      this['constructorGraph/changeDirectTypeMutation'](this.graphType)
    },
    permission() {
      this['constructorGraph/updatePermissionMutation'](this.permission)
    }
  },
  computed: {
    answersPresent() {
      return this.graphEditorVisible && this.pluginResult
    },
    plugins() {
      return this.$store.state.plugins.plugins
    },
    graphData() {
      return this.$store.state.constructorGraph
    },
    answers() {
      return this.$store.state.plugins.answers
    }
  },
  methods: {
    ...mapActions(['tasks/addTaskAction']),
    ...mapMutations([
        'constructorGraph/graphPresentMutation',
        'constructorGraph/changeDirectTypeMutation',
        'constructorGraph/updatePermissionMutation',
        'plugins/addAnswerMutation'
    ]),
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
                "answer" : this.values[i]
              })
            }
          }
        }

        let graph = null;

        if (this.graph) {
          graph = {
            "vertexCount" : this.graphData.vertexCount,
            "edgeCount" : this.graphData.edgeCount,
            "vertexes" : this.graphData.constructorGraph,
            "graphType" : this.$store.state.constructorGraph.direct ? "DIRECTED" : "UNDIRECTED"
          }
        }

        const taskPermission = {
          "edit" : this.permission.edit,
          "color" : this.permission.color,
          "weight" : this.permission.weight,
          "remove" : this.permission.remove
        }
        const data = {
          "name" : this.taskName,
          "category" : this.taskCategory,
          "algAnswerList" : algAnswerList,
          "taskPermission" : taskPermission,
          "graph" : graph,
          "graphType" : this.$store.state.constructorGraph.direct ? "DIRECTED" : "UNDIRECTED"
        }
        this['tasks/addTaskAction'](data)

      }
    },
    findPluginResult() {
      this.pluginResult = true
      const graph = {
        "vertexCount": this.graphData.vertexCount,
        "edgeCount": this.graphData.edgeCount,
        "vertexes": this.graphData.constructorGraph,
        "graphType": this.$store.state.constructorGraph.direct ? "DIRECTED" : "UNDIRECTED"
      }

      this.$http.post('/task/answers', graph).then(response => {
        this['plugins/addAnswerMutation'](response.data)
      })
    }
  }
}
</script>

<style scoped>

</style>