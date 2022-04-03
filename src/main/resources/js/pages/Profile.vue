<template>
  <v-row>
    <v-col cols="4">
      <user-info></user-info>
    </v-col>
    <v-col>
      <task-history-card></task-history-card>
    </v-col>
  </v-row>
</template>

<script>
import UserInfo from "../components/profile/UserInfo.vue";
import TaskHistoryCard from "../components/profile/TaskHistoryCard.vue";

export default {
  name: "Profile",
  components: {TaskHistoryCard, UserInfo},
  data: () => ({
  })
}
</script>

<style scoped>

</style>