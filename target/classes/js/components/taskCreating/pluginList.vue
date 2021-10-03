<template>
  <v-container>
    <v-dialog
        v-model="dialog"
        persistent
    >
      <v-card>
        <v-card-title>
          <span class="text-h5">Описание задачи</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-textarea
                :value="taskDescriptionText"
                v-model="taskDescriptionText"
                auto-grow
                filled>

            </v-textarea>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              @click="dialog = false"
          >
            Сохранить и закрыть
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-col>
      <v-card
          class="mx-auto"

      >
        <v-card-text class="ma-4">
          <v-row>
            <v-col>
              <p class="text-h4 text--primary">
                Описание задачи
              </p>
              <span  style="white-space: pre-line">
                {{taskDescriptionText}}
              </span>
              <v-btn color="primary"
                     dark
                     @click="dialog = true">
                Изменить описание вручуную
              </v-btn>
              <v-switch
                  v-model="graph"
                  label="Граф заранее построен"></v-switch>
            </v-col>

          </v-row>

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
              <p class="text-h7  text--primary">
                Тип графа:
              </p>
              <v-btn-toggle
                  v-model="graphType"
                  tile
                  top
                  color="primary"
                  group
              >
                <v-btn
                    :value="true"
                    id="directed-type-graph"
                >

                  <v-icon>moving</v-icon>
                </v-btn>

                <v-btn
                    :value="false"
                    id="undirected-type-graph">
                  <v-icon>show_chart</v-icon>
                </v-btn>
              </v-btn-toggle>
            </v-col>
            <v-col>
              <p class="text-h7  text--primary">
                Ограничения конструктора
              </p>

              <v-switch
                  class="mx-2"
                  v-model="permission.draw"
                  label="Строительство"></v-switch>
              <v-switch
                  class="mx-2"
                  v-model="permission.color"
                  label="Цвета"></v-switch>
              <v-switch
                  class="mx-2"
                  v-model="permission.edit"
                  label="Изменения"></v-switch>
              <v-switch
                  class="mx-2"
                  v-model="permission.remove"
                  label="Удаление"></v-switch>
            </v-col>

          </v-row>

        </v-card-text>
        <v-card-actions>
          <v-col>
            <v-alert
                type="success"
                v-model="added"
                class="ma-2"
                text
                outlined
                dismissible>
              Задача успешно добавлена!
            </v-alert>
            <v-alert
                type="error"
                v-model="addedError"
                text
                class="ma-2"
                outlined
                dismissible>
              Задача с таким именем уже существует!
            </v-alert>
            <v-btn
                color="primary"
                @click="saveTask"
                dark
                class="ma-2"
            >
              Создать задачу
            </v-btn>
            <v-btn
                color="primary"
                @click="findPluginResult"
                dark
                class="ma-2"
                :disabled="!graphEditorVisible"
            >
              Узнать результаты плагинов
            </v-btn>
          </v-col>
        </v-card-actions>
      </v-card>
    </v-col>
    <v-col>
      <v-row justify="start">
        <v-col v-if="graphType"
               v-for="(plugin, i) in pluginsForDirected"
               :key="i"
        >
          <v-card
              class="mx-auto"
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
        <v-col v-if="!graphType"
               v-for="(plugin, i) in pluginsForUnDirected"
               :key="i"
        >
          <v-card
              class="mx-auto"
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
      added: false,
      addedError: false,
      graph: this.$store.state.constructorGraph.graphPresent,
      graphType: this.$store.state.constructorGraph.direct,
      permission: this.$store.state.constructorGraph.permission,
      valid: true,
      pluginResult: false,
      taskDescriptionText: '',
      dialog: false
    }
  },
  watch: {
    graph() {
      this['constructorGraph/graphPresentMutation']()
      this['constructorGraph/cleanGraphMutation']()
      this.pluginResult = false
    },
    graphType() {
      this['constructorGraph/changeDirectTypeMutation'](this.graphType)
    },
    permission() {
      this['constructorGraph/updatePermissionMutation'](this.permission)
    },
    taskDescription() {
      this.taskDescriptionText = this.taskDescription
    }
  },
  computed: {
    answersPresent() {
      return this.graphEditorVisible && this.pluginResult
    },
    pluginsForDirected() {
      return this.$store.state.plugins.plugins.filter(item => item.graphType === 'DIRECTED')
    },
    pluginsForUnDirected() {
      return this.$store.state.plugins.plugins.filter(item => item.graphType === 'UNDIRECTED')
    },
    plugins() {
      return this.$store.state.plugins.plugins
    },
    graphData() {
      return this.$store.state.constructorGraph
    },
    answers() {
      return this.$store.state.plugins.answers
    },
    taskDescription() {
      return this.setTaskDescription()
    }

  },
  mounted() {
    this.taskDescriptionText = this.taskDescription
  },
  methods: {
    ...mapActions(['tasks/addTaskAction']),
    ...mapMutations([
        'constructorGraph/graphPresentMutation',
        'constructorGraph/changeDirectTypeMutation',
        'constructorGraph/updatePermissionMutation',
        'constructorGraph/cleanGraphMutation',
        'plugins/addAnswerMutation',
        'tasks/addTaskMutation'
    ]),
    setTaskDescription() {
      let text = 'Постройте ' + (this.graphType ? 'ориентированный': 'неориентированный') +
          ' удовлетворяющий следующим условиям:' +'\n'
      for (let i = 0; i < this.plugins.length; i++) {
        if (this.selected[i] && this.values[i] && this.plugins[i].algorithmType === 'CHARACTERISTIC') {
          text = text + this.plugins[i].description + ' ' + this.values[i] +'\n'
        } else {
          if (this.selected[i]  && this.plugins[i].algorithmType === 'PROPERTY') {
            text = text + this.plugins[i].description + ' ' + (this.values[i] ? 'выполнено' : 'невыполнено') + '\n'
          }
        }
      }
      return text
    },
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
          "draw" : this.permission.draw,
          "color" : this.permission.color,
          "edit" : this.permission.edit,
          "remove" : this.permission.remove
        }
        const data = {
          "name" : this.taskName,
          "category" : this.taskCategory,
          "algAnswerList" : algAnswerList,
          "taskPermission" : taskPermission,
          "taskDescription": this.taskDescriptionText,
          "graph" : graph,
          "graphType" : this.$store.state.constructorGraph.direct ? "DIRECTED" : "UNDIRECTED"
        }
        this['tasks/addTaskAction'](data)
        this.$http.post('task', data).then(
            response => {
              this['tasks/addTaskMutation'](response.data)
              this.added = true
              this.addedError = false
            },
            err => {
              this.addedError = true
              this.added = false
            }
        )
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
.btn-toggle {
  flex-direction: column;
}
</style>