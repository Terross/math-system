<template>
  <v-card
      class="mx-auto"
  >
    <v-card-title>{{task.name}}</v-card-title>
    <v-card-subtitle>{{task.category}}</v-card-subtitle>
    <v-card-text>
      <span  style="white-space: pre-line">
        {{task.taskDescription}}
      </span>
    </v-card-text>
    <v-card-actions>
      <v-btn
          color="primary"
          class="ma-2"
          @click="solveTask(task.id)"
          dark
      >
        Решить задачу
      </v-btn>
      <v-btn
          v-if="currentRole === 'ADMIN' || currentEmail === task.authorEmail"
          color="error"
          @click="removeTask"
          class="ma-2"
          dark>
        Удалить задачу
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import {HTTP} from "../../axios/http-common";
import {mapActions, mapMutations} from "vuex";

export default {
  name: "TaskCard",
  props: {
    task: Object
  },
  computed: {
    token() {
      return this.$store.state.profile.profile.jwt
    },
    currentRole() {
      return this.$store.state.profile.profile.role
    },
    currentEmail() {
      return this.$store.state.profile.profile.email
    }
  },
  methods: {
    ...mapMutations(['tasks/removeTaskMutation']),
    solveTask(id) {
      this.$router.push({ path: `/task/${id}` })
    },
    updateTask(i) {
      this.$router.push({path : `/changeTask/${i}`})
    },
    removeTask() {
      this.$http
          .delete(`/api/v1/all/task/${this.task.id}`, {
            headers: {
              'Authorization' : "Bearer " + this.token
            }
          })
          .then(response => {
            this['tasks/removeTaskMutation'](this.task)
          })
    }
  }
}
</script>

<style scoped>

</style>