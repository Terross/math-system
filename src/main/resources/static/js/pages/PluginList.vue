<template>
  <v-container fluid>
    <v-row>
      <v-col>
        <plugin-list>
        </plugin-list>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import PluginList from "../components/plugins/PluginList.vue";
export default {
  name: "Plugins",
  components: {
    PluginList
  }
}
</script>

<style scoped>

</style>