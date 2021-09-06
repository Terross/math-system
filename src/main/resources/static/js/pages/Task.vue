<template>
  <v-container fluid>
    {{$route.params.id}}
  <v-row>
    <v-col>

    </v-col>
    <v-col>

    </v-col>
  </v-row>
  </v-container>
</template>

<script>
export default {
  name: "Task",
  data() {
    return {
      graph: null
    }
  },
  mounted() {
    this.$http.get('/graph/api/getGraph/78').then(response => {
      console.log(response)
    })
  }
}
</script>

<style scoped>

</style>