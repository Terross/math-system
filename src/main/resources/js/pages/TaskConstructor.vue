<template>
  <v-container fluid>
    <v-row>
      <GraphEditor v-if="graphEditorVisible"></GraphEditor>
      <v-col>
        <MainTaskEditor></MainTaskEditor>
        <PluginsList></PluginsList>
      </v-col>
    </v-row>
  </v-container>

</template>

<script>
import MainTaskEditor from "../components/taskCreating/MainTaskEditor.vue";
import PluginsList from "../components/taskCreating/PluginsList.vue";
import GraphEditor from "../components/cylc/graphEditor.vue";
import {mapMutations} from "vuex";

export default {
  name: "TaskConstructor",
  components: {PluginsList, MainTaskEditor, GraphEditor},
  computed: {
    graphEditorVisible() {
      return this.$store.state.tasks.currentTask.graphIsPresent
    }
  }
}
</script>

<style scoped>

</style>