<template>
  <v-col>
    <v-row justify="start">
      <v-col v-if="graphType"
             v-for="(plugin, i) in pluginsForDirected"
             :key="i"
      >
        <PluginCard :plugin="plugin"></PluginCard>
      </v-col>
      <v-col v-if="!graphType"
             v-for="(plugin, i) in pluginsForUnDirected"
             :key="i"
      >
        <PluginCard :plugin="plugin"></PluginCard>
      </v-col>
    </v-row>
  </v-col>
</template>

<script>
import {mapActions, mapMutations} from "vuex";
import PluginCard from "./PluginCard.vue";

export default {
  name: "PluginsList",
  components: {PluginCard},
  data() {
    return {
      valuesForDirected: [],
      valuesForUndirected: [],
      selectedForDirected: [],
      selectedForUndirected: [],
    }
  },
  computed: {
    graphType() {
      return this.$store.state.tasks.currentTask.graphDirect
    },
    pluginsForDirected() {
      return this.$store.state.plugins.plugins
          .filter(item => item.graphType !== 'UNDIRECTED')
    },
    pluginsForUnDirected() {
      return this.$store.state.plugins.plugins
          .filter(item => item.graphType !== 'DIRECTED')
    }
  }
}
</script>

<style scoped>

</style>