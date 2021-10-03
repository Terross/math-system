<template>
  <v-container>
    <v-col>

          <navigation-graph :networkType="networkType">
          </navigation-graph>


          <network :networkType="networkType"
                   :config-elements="configElements">
          </network>

    </v-col>

  </v-container>

</template>

<script>
import network from "./network.vue";
import NavigationGraph from "./naviagationGraph.vue";
import {mapGetters, mapMutations} from 'vuex'

export default {
  data() {
    return {
      networkType: this.$route.path.includes('/task/') ? 'task' : 'constructor'
    }
  },
  props: {
    configElements: Array,
    graphType: Boolean
  },
  name: 'graphEditor',
  components: {
    'navigationGraph': NavigationGraph,
    'network': network
  },
  methods: {
    ...mapMutations(['constructorGraph/selectedDataMutation'])
  }

}
</script>

<style scoped>

</style>