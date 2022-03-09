<template>
  <v-row>
    <v-col cols="4">
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
            <v-list-item v-if="profile.group.length > 0">
              <v-list-item-content>
                <v-list-item-title v-text="'Группа'"></v-list-item-title>
                <v-list-item-subtitle v-text="profile.group"></v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-card-text>
        <v-card-actions>
          <v-btn
              color="primary"
              class="ma-auto"
              @click="update"
          >
            Изменить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import {mapState, mapGetters, mapMutations} from 'vuex'

export default {
  name: "Profile",
  data: () => ({
  }),
  computed: {
    profileVuex: {
      ...mapState({get: 'profile'})
    },
    profile() {
      return this.profileVuex.profile
    }
  },
  methods: {
    update() {
      console.log("update")
    }
  }
}
</script>

<style scoped>

</style>