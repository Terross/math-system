<template>
  <v-card
  min-height="85vh"
  color="indigo lighten-5">
    <v-card-text>
      <v-row>
        <navigation-graph>
        </navigation-graph>
      </v-row>
      <v-row style="height: 85vh;">
        <network :graph-exist="graphExist">
        </network>
      </v-row>
    </v-card-text>




  </v-card>
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