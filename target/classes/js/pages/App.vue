<template>


  <v-app id="inspire">

    <v-app-bar
        app
        color="indigo lighten-1"
        dark
        height="64px"
    >
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
      <v-toolbar-title
      class="ma-4">Модуль графы</v-toolbar-title>
      <div v-if="$route.path === '/'">
        Конструктор задач
      </div>
      <div v-if="$route.path === '/tasks'">
        Список задач
      </div>
      <div v-if="$route.path === '/plugins'">
        Плагины
      </div>

    </v-app-bar>

    <v-navigation-drawer
        v-model="drawer"
        fixed
        app
        temporary
    >
      <v-list
          nav
          dense
      >
        <v-list-item-group
            v-model="group"
            active-class="indigo lighten-1"
        >
          <v-list-item
              :disabled="$route.path === '/'"
              @click="showTaskConstructor"
          >
            <v-list-item-icon>
              <v-icon>edit</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Конструктор задач</v-list-item-title>
          </v-list-item>

          <v-list-item
              :disabled="$route.path === '/tasks'"
              @click="showTaskList">
            <v-list-item-icon>
              <v-icon>task</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Список задач</v-list-item-title>
          </v-list-item>

          <v-list-item
              :disabled="$route.path === '/plugins'"
              @click="showPlugins">
            <v-list-item-icon>
              <v-icon>extension</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Модули</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>


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
      drawer: false,
      group: null,
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