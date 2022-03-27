<template>
  <v-card>
    <v-card-title>Ограничения конструктора</v-card-title>
    <v-card-text>
      <v-switch
          class="mx-2"
          v-model="draw"
          label="Строительство"></v-switch>
      <v-switch
          class="mx-2"
          v-model="color"
          label="Цвета"></v-switch>
      <v-switch
          class="mx-2"
          v-model="edit"
          label="Изменения"></v-switch>
      <v-switch
          class="mx-2"
          v-model="remove"
          label="Удаление"></v-switch>
    </v-card-text>
  </v-card>
</template>

<script>
import {mapMutations} from "vuex";
export default {
  name: "TaskPermissionCard",
  data() {
    return {
      draw: true,
      color: true,
      edit: true,
      remove: true
    }
  },
  watch: {
    draw: 'editPermission',
    color: 'editPermission',
    edit: 'editPermission',
    remove: 'editPermission'
  },
  methods: {
    ...mapMutations(['tasks/editCurrentTaskPermissionMutation']),
    editPermission() {
      this['tasks/editCurrentTaskPermissionMutation']({
        draw: this.draw,
        color: this.color,
        edit: this.edit,
        remove: this.remove
      })
    }
  }


}
</script>

<style scoped>

</style>