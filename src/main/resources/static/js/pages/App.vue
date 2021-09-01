<template>


  <v-app id="inspire">

    <v-app-bar
        app
        color="indigo lighten-1"
        dark
        dense
        height="64px"
    >
      <v-toolbar-title>Math System</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

    </v-app-bar>

    <v-main>
      <task-list>

      </task-list>
    </v-main>

  </v-app>
</template>

<script>
import GraphEditor from "../components/cylc/graphEditor.vue";
import TaskList from "./taskList.vue";
export default {
  components: {TaskList, GraphEditor}
}
</script>

<style>
#main-container {
  width: 100%;
  height: 100%;
}
</style>