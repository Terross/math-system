<template>
  <v-container>
    <v-data-table
        :headers="headers"
        :items="users"
        sort-by="firstName"
        class="elevation-1"
    >
      <template v-slot:item.role="{ item }">
        <span>
          {{item.role === "ROLE_USER" ? "USER" : "ADMIN"}}
        </span>
        <v-icon
            v-if="currentRole === 'ADMIN' && item.role === 'ROLE_USER'"
            small
            class="mr-2"
            @click="upToAdmin(item)"
        >
          arrow_upward
        </v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import {HTTP} from "../axios/http-common";
import { mapState, mapGetters, mapMutations } from "vuex";

export default {
  name: "Users",

  data: () => ({
    headers: [
      { text: 'Имя', value: 'firstName'},
      { text: 'Фамилия', value: 'lastName' },
      { text: 'Отчество', value: 'patronymic' },
      { text: 'email', value: 'email' },
      { text: 'Группа', value: 'userGroup' },
      { text: 'Роль', value: 'role', sortable: false },
    ],
    users: []
  }),

  created () {
    this.initialize()
  },

  computed: {
    token() {
      return this.$store.state.profile.profile.jwt
    },
    currentRole() {
      return this.$store.state.profile.profile.role
    }
  },

  methods: {
    initialize () {
      console.log(this.token)
      this.$http
          .get('/api/v1/all/allUsers', {
            headers: {
              'Authorization' : "Bearer " + this.token
            }
          })
          .then(response => {
            console.log(response.data)
            this.users = response.data
          })
          .catch(e => {
            console.log(e)
          })
    },

    upToAdmin (user) {
      console.log(this.token)

      this.$http
          .put(`/api/v1/admin/user/up/${user.email}`, '',{
            headers: {
              'Authorization' : "Bearer " + this.token
            }
          })
          .then(response => {
            this
                .users
                .find(item => user.email === item.email)
                .role = response.data.role
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