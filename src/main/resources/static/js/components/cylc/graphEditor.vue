<template>
  <v-container class = "canv">
    <v-row no-gutters style="margin-bottom: -24px">
      <navigation-graph>
      </navigation-graph>
    </v-row>
    <v-row style="height: 95%" >
      <network>
      </network>
    </v-row>
  </v-container>
</template>

<script>
import network from "./network.vue";
import NavigationGraph from "./naviagationGraph.vue";
import { mapGetters } from 'vuex'

export default {
  name: 'graphEditor',
  components: {
    'navigationGraph': NavigationGraph,
    'network': network},
  methods: {
  }
}
</script>

<style scoped>
.canv {
  height: 75vh;
  min-width: 900px;
}
</style>