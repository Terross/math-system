<template>
  <v-col>
    <v-alert
        v-if="alertType === 'success'"
        type="success"
        text
        outlined
        class="ma-4"
        dismissible>
      {{alertMessage}}
    </v-alert>
    <v-alert
        v-else-if="alertType === 'error'"
        type="error"
        text
        outlined
        class="ma-4"
        dismissible>
      {{alertMessage}}
    </v-alert>
  </v-col>
</template>

<script>
export default {
  name: "PluginAlerts",
  props: {
    alertType: String,
    alertMessage: String
  }
}
</script>

<style scoped>

</style>