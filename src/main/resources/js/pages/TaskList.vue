<template>
<v-container>
  <v-row>
    <v-col>
      <v-card>
        <v-card-title>Фильтрация задач</v-card-title>
        <v-card-actions>
          <v-row>
            <v-text-field
                v-model="nameFilter"
                label="Поиск по имени задачи"
                class="ma-4"

            ></v-text-field>
            <v-text-field
                v-model="categoryFilter"
                label="Поиск по имени плагина"
                class="ma-4"

            ></v-text-field>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-col>

  </v-row>
  <v-row justify="start">
    <v-col
        v-for="(task, i) in tasks"
        :key="i">
      <task-card :task="task" v-if="(task.name.includes(nameFilter) || nameFilter.length === 0) &&
                                  (task.category.includes(categoryFilter) || categoryFilter.length === 0)"></task-card>
    </v-col>
  </v-row>

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
  data() {
    return {
      nameFilter: '',
      categoryFilter: ''
    }
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