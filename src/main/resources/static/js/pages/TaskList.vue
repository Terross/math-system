<template>
<v-container>
  <v-col
      v-for="(task, i) in tasks"
      :key="i"
      cols="12">
    <v-card
        class="mx-auto"
        min-width="344"
    >
      <v-card-text>
        <p class="text-h4 text--primary">
          {{task.name}}
        </p>
        <div class="text--primary">
          {{task.category}}
        </div>
        <div v-for="(algA, i) in task.algAnswerList"  class="text--primary">
          {{algA.algorithm.description + " : " + algA.answer + "\n"}}
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn
            color="indigo lighten-1"
            @click="solveTask(task.id)"
            dark
        >
          Решить задачу
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-col>
</v-container>
</template>

<script>
import graphEditor from "../components/cylc/graphEditor.vue";
import TaskDescription from "../components/cylc/taskDescription.vue";
export default {
  name: "taskList",
  components: {
    TaskDescription,
    graphEditor
  },
  data() {
    return {
      tasks : this.$store.state.tasks
    }
  },
  methods : {
    solveTask(i) {
      this.$router.push({ path: `/task/${i}` })
    },
    updateTask() {

    },
    removeTask() {

    }
  }

}
</script>

<style scoped>

</style>