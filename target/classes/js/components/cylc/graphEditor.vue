<template>
  <v-container>
      <v-row>
        <navigation-graph>
        </navigation-graph>
      </v-row>
      <v-row style="height: 85vh;">
        <network :graph-exist="graphExist">
        </network>
      </v-row>
  </v-container>
</template>

<script>
import network from "./network.vue";
import NavigationGraph from "./naviagationGraph.vue";
import { mapGetters } from 'vuex'

export default {
  props: {
    graphExist: Boolean
  },
  name: 'graphEditor',
  components: {
    'navigationGraph': NavigationGraph,
    'network': network},
  methods: {
  }
}
</script>

<style scoped>

</style>