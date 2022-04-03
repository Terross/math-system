<template>

  <v-data-table
      :headers="headers"
      :items="taskStories"
      class="ma-4"
  >
    <template v-slot:item.successfully="{ item }">
      <v-chip
          :color="item.successfully ? 'green' : 'red'"
          dark
      >
        {{ item.successfully ? 'Верно' : 'Ошибка' }}
      </v-chip>
    </template>
  </v-data-table>
</template>

<script>
import {HTTP} from "../../axios/http-common.js";
export default {
  name: "TaskHistoryCard",
  data() {
    return {
      headers: [
        {text: 'Название', value: 'name'},
        {text: 'Успех', value: 'successfully'},
        {text: 'Дата', value: 'time'}
      ],
      taskStories: []
    }
  },
  computed: {
    email() {
      return this.$store.state.profile.profile.email
    },
    token() {
      return this.$store.state.profile.profile.jwt
    }
  },
  beforeMount() {
    console.log(123)
    this.$http
        .get(`/api/v1/all/user/task-history/${this.email}`, {
          headers: {
            'Authorization' : "Bearer " + this.token
          }
        })
        .then(response => this.taskStories = response.data)
    console.log(this.taskStories)
  }
}
</script>

<style scoped>

</style>