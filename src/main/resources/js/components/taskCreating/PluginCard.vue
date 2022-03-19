<template>
  <v-card
      class="mx-auto"
  >
    <v-card-title>{{plugin.name}}</v-card-title>
    <v-card-subtitle>{{plugin.description}}</v-card-subtitle>
    <v-card-text>
      <v-text-field v-if="plugin.pluginType === 'CHARACTERISTIC'"
                    v-model="value"
                    label="Требуемое значение"
                    required
      ></v-text-field>
      <v-switch v-if="plugin.pluginType !== 'CHARACTERISTIC'"
                v-model="value"
                :label="`Требование: ${value? ' выполняется' :  'невыполняется'}`"></v-switch>
      <v-switch
          v-model="selected"
          label="Добавить плагин в задачу"></v-switch>
    </v-card-text>
  </v-card>
</template>

<script>
import {mapMutations} from "vuex";

export default {
  name: "PluginCard",
  props: {
    plugin: Object
  },
  data() {
    return {
      value: '',
      selected: ''
    }
  },
  watch: {
    value: 'editValue',
    selected: 'editSelected'
  },
  methods: {
    ...mapMutations([
        'tasks/addPluginToCurrentTask',
        'tasks/removePluginFormCurrentTask'
    ]),
    editSelected() {
      if (this.selected && this.value) {
        this['tasks/addPluginToCurrentTask']({
          plugin: this.plugin,
          value: this.value
        })
      } else {
        this['tasks/removePluginFormCurrentTask']({
          plugin: this.plugin,
          value: this.value
        })
      }
    },
    editValue() {
      if (this.selected) {
        this['tasks/addPluginToCurrentTask']({
          plugin: this.plugin,
          value: this.value
        })
      }
      if (!this.value) {
        this['tasks/removePluginFormCurrentTask']({
          plugin: this.plugin,
          value: this.value
        })
      }
    }
  }
}
</script>

<style scoped>

</style>