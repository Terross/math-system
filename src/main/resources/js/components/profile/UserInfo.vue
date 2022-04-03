<template>
  <v-card class="ma-4">
    <v-card-title >
      Профиль пользователя
    </v-card-title>
    <v-card-subtitle>
      {{profile.email}}
    </v-card-subtitle>
    <v-card-text>
      <v-list three-line>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title v-text="'Имя'"></v-list-item-title>
            <v-list-item-subtitle v-text="profile.firstName"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title v-text="'Фамилия'"></v-list-item-title>
            <v-list-item-subtitle v-text="profile.lastName"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-list-item v-if="profile.patronymic">
          <v-list-item-content>
            <v-list-item-title v-text="'Отчество'"></v-list-item-title>
            <v-list-item-subtitle v-text="profile.patronymic"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title v-text="'Почта'"></v-list-item-title>
            <v-list-item-subtitle v-text="profile.email"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title v-text="'Роль'"></v-list-item-title>
            <v-list-item-subtitle v-text="profile.role"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-list-item v-if="profile.group">
          <v-list-item-content>
            <v-list-item-title v-text="'Группа'"></v-list-item-title>
            <v-list-item-subtitle v-text="profile.group"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-card-text>
  </v-card>
</template>

<script>
import {mapState, mapGetters, mapMutations} from 'vuex'

export default {
  name: "UserInfo",
  computed: {
    profileVuex: {
      ...mapState({get: 'profile'})
    },
    profile() {
      return this.profileVuex.profile
    }
  }
}
</script>

<style scoped>

</style>