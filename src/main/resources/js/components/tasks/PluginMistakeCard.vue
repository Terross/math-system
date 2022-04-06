<template>
  <v-card>
    <v-card-title>{{'Список ошибок '}}</v-card-title>
    <v-list>
      <v-list-item-group color="primary">
        <v-list-item
            v-for="(mistake, i) in mistakes"
            :key="i"
        >
          <v-list-item-content>
            <v-list-item-title>{{mistake.plugin.description + ' = ' + mistake.result}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>

<script>
export default {
  name: "PluginMistakeCard",
  props: {
    mistakes: Array
  },
  methods: {
    findPlugin(mistake) {
      return this.plugins.find(plugin => plugin.id === mistake.plugin.id)
    }
  }
}
</script>

<style scoped>

</style>