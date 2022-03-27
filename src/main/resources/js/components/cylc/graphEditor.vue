<template>
  <v-col>
      <navigation-graph></navigation-graph>
      <network :config-elements="configElements"></network>
  </v-col>
</template>

<script>
import network from "./network.vue";
import NavigationGraph from "./naviagationGraph.vue";

export default {
  data() {
    return {
      networkType: this.$route.path.includes('/task/') ? 'task' : 'constructor'
    }
  },
  props: {
    configElements: Array
  },
  name: 'graphEditor',
  components: {
    'navigationGraph': NavigationGraph,
    'network': network
  }

}
</script>

<style scoped>

</style>