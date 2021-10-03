<template>
  <v-card
      class="mx-auto"
  >
    <v-card-text class="mx-2">
      <p class="text-h4 text--primary">
        {{plugin.name}}
      </p>
      <p class="text-h6 text--primary">
        {{'Имя файла: ' + plugin.fileName}}
      </p>
      <p class="text-h7 text--primary">
        {{plugin.algorithmType === 'CHARACTERISTIC' ?  'Характеристика' : 'Свойство'}}
        {{plugin.graphType === 'DIRECTED' ?  'для ориентированного графа' : 'для неориентированного графа'}}
      </p>
      <div class="text--primary">
        {{plugin.description}}
      </div>
    </v-card-text>
    <v-card-actions>
      <v-btn
          color="error"
          @click="removePlugin(plugin.id)"
          class="ma-4"
          dark>
        Удалить плагин
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "PluginCard",
  props: {
    plugin: Object
  },
  methods: {
    removePlugin(id) {
      this.$http.delete(`/plugin/api/${id}`).then(response => {
        if (response.ok) {
          this['plugins/removePluginMutation'](this.plugins.filter(item => item.id === id)[0])
        }
      })
    }
  }
}
</script>

<style scoped>

</style>