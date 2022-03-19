<template>
  <v-col>
    <v-card
        class="mx-auto"
    >
      <v-card-title>Конструктор задач</v-card-title>
      <v-card-text>
        <v-row justify="center">
          <v-col>
            <TaskDescriptionCard></TaskDescriptionCard>
          </v-col>
        </v-row>
        <v-row justify="center">
            <v-col>
              <TaskDataCard></TaskDataCard>
            </v-col>
            <v-col>
              <TaskPermissionCard></TaskPermissionCard>
            </v-col>
        </v-row>
      </v-card-text>
      <v-card-actions>
        <v-col>
          <!--          <v-alert-->
          <!--              type="success"-->
          <!--              v-model="added"-->
          <!--              class="ma-2"-->
          <!--              text-->
          <!--              outlined-->
          <!--              dismissible>-->
          <!--            Задача успешно добавлена!-->
          <!--          </v-alert>-->
          <!--          <v-alert-->
          <!--              type="error"-->
          <!--              v-model="addedError"-->
          <!--              text-->
          <!--              class="ma-2"-->
          <!--              outlined-->
          <!--              dismissible>-->
          <!--            Задача с таким именем уже существует!-->
          <!--          </v-alert>-->
          <v-btn
              color="primary"
              @click="saveTask"
              dark
              class="ma-2"
          >
            Создать задачу
          </v-btn>
        </v-col>
      </v-card-actions>
    </v-card>
  </v-col>
</template>

<script>
import TaskDescriptionCard from "./TaskDescriptionCard.vue";
import {mapActions, mapMutations} from "vuex";
import TaskDataCard from "./TaskDataCard.vue";
import TaskPermissionCard from "./TaskPermissionCard.vue";

export default {
  name: "MainTaskEditor",
  components: {TaskPermissionCard, TaskDataCard, TaskDescriptionCard},
  data() {
    return {
    }
  },
  methods: {
    ...mapMutations([
        'tasks/editCurrentTaskGraphEnableMutation',
        'tasks/editCurrentTaskGraphTypeMutation'
    ]),
    saveTask() {
      console.log('save')
    }
  }
}
</script>

<style scoped>

</style>