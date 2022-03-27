<template>
  <v-col>
    <v-card
        class="mx-auto"
    >
      <v-card-title>Конструктор задач</v-card-title>
      <v-card-text>
        <v-row justify="center">
          <v-col>
            <TaskDescriptionCard></TaskDescriptionCard>
          </v-col>
        </v-row>
        <v-row justify="center">
            <v-col>
              <TaskDataCard></TaskDataCard>
            </v-col>
            <v-col>
              <TaskPermissionCard></TaskPermissionCard>
            </v-col>
        </v-row>
      </v-card-text>
      <v-card-actions>
        <v-col>
          <!--          <v-alert-->
          <!--              type="success"-->
          <!--              v-model="added"-->
          <!--              class="ma-2"-->
          <!--              text-->
          <!--              outlined-->
          <!--              dismissible>-->
          <!--            Задача успешно добавлена!-->
          <!--          </v-alert>-->
          <!--          <v-alert-->
          <!--              type="error"-->
          <!--              v-model="addedError"-->
          <!--              text-->
          <!--              class="ma-2"-->
          <!--              outlined-->
          <!--              dismissible>-->
          <!--            Задача с таким именем уже существует!-->
          <!--          </v-alert>-->
          <v-btn
              color="primary"
              @click="saveTask"
              dark
              class="ma-2"
          >
            Создать задачу
          </v-btn>
        </v-col>
      </v-card-actions>
    </v-card>
  </v-col>
</template>

<script>
import TaskDescriptionCard from "./TaskDescriptionCard.vue";
import {mapActions, mapMutations, mapGetters} from "vuex";
import TaskDataCard from "./TaskDataCard.vue";
import TaskPermissionCard from "./TaskPermissionCard.vue";
import {HTTP} from "../../axios/http-common";

export default {
  name: "MainTaskEditor",
  components: {TaskPermissionCard, TaskDataCard, TaskDescriptionCard},
  data() {
    return {
    }
  },
  computed: {
    task() {
      return this.$store.state.tasks.currentTask
    },
    token() {
      return this.$store.state.profile.profile.jwt
    },
    author() {
      return this.$store.state.profile.profile
    },
    graph() {
      return this.task.graph
    }
  },
  methods: {
    ...mapMutations([
        'tasks/editCurrentTaskGraphEnableMutation',
        'tasks/editCurrentTaskGraphTypeMutation',
        'tasks/addTaskMutation'
    ]),
    ...mapGetters(['tasks/generatedDescription']),
    saveTask() {
      const description = this.task.description ? this.task.description : this['tasks/generatedDescription']()
      const data = {
        "category": this.task.category,
        "name": this.task.name,
        "authorEmail": this.author.email,
        "graphType": this.task.graphDirect ? "DIRECTED" : "UNDIRECTED",
        "taskDescription": description,
        "graph" : {
          "vertexCount" : this.graph.vertexCount,
          "edgeCount" : this.graph.edgeCount,
          "vertexList" : this.graph.vertexList,
          "edgeList": this.graph.edgeList,
          "directType" : this.task.graphDirect ? "DIRECTED" : "UNDIRECTED"
        },
        "taskPermission": this.task.permission,
        "pluginValues": this.task.plugins,
        "graphIsPresent": this.task.graphIsPresent
      }
      console.log(data)
      HTTP
          .post('/all/task/task', data, {
            headers: {
              'Authorization' : "Bearer " + this.token
            }
          })
          .then(response => {
            this['tasks/addTaskMutation'](response.data)
          })
          .catch(e => {
            console.log(e)
          })
    }
  }
}
</script>

<style scoped>

</style>