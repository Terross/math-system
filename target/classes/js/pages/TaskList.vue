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
          {{algA.algorithm.description + " " +
        ((algA.type === 'characteristic') ? algA.answer :
            (algA.answer?': выполнено':': невыполнено'))}}
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
        <v-btn
            color="red lighten-1"
            @click="removeTask(task.id)"
            dark>
          Удалить задачу
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-col>
</v-container>
</template>

<script>
import graphEditor from "../components/cylc/graphEditor.vue";
import TaskDescription from "../components/cylc/taskDescription.vue";
import {mapActions, mapMutations} from "vuex";
export default {
  name: "taskList",
  components: {
    TaskDescription,
    graphEditor
  },
  // data() {
  //   return {
  //     tasks: null
  //   }
  // },
  computed : {
    tasks() {
      return this.$store.state.tasks.tasks
    }
  },
  methods : {
    ...mapMutations(['constructorGraph/initMutation']),
    ...mapActions(['tasks/removeTaskAction']),
    solveTask(id) {
      this.$router.push({ path: `/task/${id}` })
    },
    updateTask(i) {
      this.$router.push({path : `/changeTask/${i}`})
    },
    removeTask(id) {
      this['tasks/removeTaskAction'](this.tasks.filter(item => item.id === id)[0])

    }
  }

}
</script>

<style scoped>

</style>