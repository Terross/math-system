<template>


  <v-app id="inspire">

    <v-app-bar
        app
        color="indigo lighten-1"
        dark
        dense
        height="64px"
    >
      <v-toolbar-title
      class="ma-4">Модуль графы</v-toolbar-title>
      <v-btn
          color="white"
          text
          class="ma-4 white--text"
          :disabled="$route.path === '/'"
          @click="showTaskConstructor"
      >
        Конструктор задач
      </v-btn>

      <v-btn
          color="white"
          text
          class="ma-4 white--text"
          :disabled="$route.path === '/tasks'"
          @click="showTaskList"
      >
        Список задач
      </v-btn>

      <v-btn
          color="white"
          text
          class="ma-4 white--text"
          :disabled="$route.path === '/plugins'"
          @click="showPlugins"
      >
        Загрузка модулей
      </v-btn>
      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>


    </v-app-bar>



    <v-main>
      <router-view></router-view>
    </v-main>


  </v-app>
</template>

<script>
import GraphEditor from "../components/cylc/graphEditor.vue";
import TaskList from "./TaskList.vue";
import taskConstructor from "./TaskConstructor.vue";
export default {
  components: {TaskList, GraphEditor, taskConstructor},
  data() {
    return {
      tab: null,
      items: [
          'Сборник задач', 'Конструктор задач', 'Новые модули'
      ]
    }
  },
  methods: {
    showTaskConstructor() {
      this.$router.push('/')
    },
    showTaskList() {
      this.$router.push('/tasks')
    },
    showPlugins() {
      this.$router.push('/plugins')
    }
  }
}
</script>

<style>
#main-container {
  width: 100%;
  height: 100%;
}
</style>