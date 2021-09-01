<template>
  <v-container style="height: 100%; margin-left: 0;">
    <v-row no-gutters style="margin-bottom: -24px">
      <navigation-graph
          @clicked="childFrom"></navigation-graph>
    </v-row>
    <v-row style="height: 95%" >
      <network
      ref="network">
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
    childFrom (value) {
      this.newGraphLabel = value
    }
  }
}
</script>

<style scoped>

</style>