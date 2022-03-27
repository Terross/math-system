<template>
  <v-container>
    <v-col>
      <v-card
          class="mx-auto"
      >
        <v-card-title>{{ task.name }}</v-card-title>
        <v-card-subtitle>{{task.category}}</v-card-subtitle>
        <v-card-text>
          <span  style="white-space: pre-line">
                  {{task.taskDescription}}
          </span>

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

      <plugin-mistake-card v-if="pluginMistake.length> 0"
                           :mistakes="pluginMistake"></plugin-mistake-card>
    </v-col>
  </v-container>
</template>

<script>
import {HTTP} from "../../axios/http-common.js";
import PluginMistakeCard from "./PluginMistakeCard.vue";
export default {
  name: "TaskSolution",
  components: {PluginMistakeCard},
  props: {
    task: Object
  },

  computed: {
    graph() {
      return this.$store.state.currentGraph
    },
    token() {
      return this.$store.state.profile.profile.jwt
    },
    email() {
      return this.$store.state.profile.profile.email
    }
  },
  data() {
    return {
      result: "wrong",
      solutionResponse: Object,
      rightAnswer: false,
      wrongAnswer: false,
      pluginMistake: []
    }
  },
  methods: {
    verify() {
      const data = {
        "graphProjection": {
          "vertexCount" : this.graph.vertexCount,
          "edgeCount" : this.graph.edgeCount,
          "vertexList" : this.graph.vertexList,
          "edgeList": this.graph.edgeList,
          "directType" : this.task.graphType
        },
        "email" : this.email
      }

      HTTP
          .post(`all/task/solution/${this.$route.params.id}`, data,{
            headers: {
              'Authorization' : "Bearer " + this.token
            }
          })
          .then(response => {
            console.log(response.data)
            const answer = response.data
            this.rightAnswer = answer.right
            this.wrongAnswer = !answer.right
            this.pluginMistake = answer.pluginResultList
          })
          .catch(e => console.log(e))
    }
  }
}
</script>

<style scoped>

</style>