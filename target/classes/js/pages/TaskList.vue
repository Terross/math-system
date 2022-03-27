<template>
<v-container>
  <v-col
      v-for="(task, i) in tasks"
      :key="i"
      cols="12">
    <task-card :task="task"></task-card>
  </v-col>
</v-container>
</template>

<script>
import graphEditor from "../components/cylc/graphEditor.vue";
import TaskDescription from "../components/cylc/taskDescription.vue";
import {mapActions, mapMutations} from "vuex";
import TaskCard from "../components/tasks/TaskCard.vue";
export default {
  name: "taskList",
  components: {
    TaskCard,
    TaskDescription,
    graphEditor
  },
  computed : {
    tasks() {
      return this.$store.state.tasks.tasks
    }
  }
}
</script>

<style scoped>

</style>