<template>
  <v-container class="grey lighten-5" fluid style="height: 100%;">
    <v-row style="height: 100%;">
      <v-col cols="12"
             sm="8"
             style="height: 100%;"
      >
        <graph-editor></graph-editor>
      </v-col>
      <v-col>
        <task-description
            style="margin-top: 10px"
            :name="'Задача'"
            :category="'Категория'"
            :description="'Описание задачи'"></task-description>
      </v-col>

    </v-row>
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
  }
}
</script>

<style scoped>

</style>