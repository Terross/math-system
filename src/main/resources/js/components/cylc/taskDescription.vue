<template>
  <v-card
      class="mx-auto"
  >
    <v-card-text>
      <p class="display-1 text--primary">
        {{ name }}
      </p>
      <p>{{category}}</p>
      <div class="text--primary">
        {{description}}
      </div>
    </v-card-text>
    <v-card-actions>
      <v-btn
          dark
          color="indigo lighten-1"
      >
        Отправить решение
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "taskDescription",
  props: {
    name: String,
    category: String,
    description: String
  }

}
</script>

<style scoped>

</style>