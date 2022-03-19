<template>
  <v-card>
    <v-card-title>Данные о задаче</v-card-title>
    <v-card-text>
      <v-form
          ref = "form"
          v-model = "valid"
          lazy-validation>
        <v-text-field
            v-model="taskName"
            label="Название задачи"
            :rules="[v => !!v || 'Требуется название задачи']"
            required
        ></v-text-field>
        <v-text-field
            v-model="taskCategory"
            label="Категория задачи"
            :rules="[v => !!v || 'Требуется категория задачи']"
            required
        ></v-text-field>
      </v-form>
      <p class="text-h7  text--primary">
        Тип графа:
      </p>
      <v-btn-toggle
          v-model="graphType"
          tile
          top
          color="primary"
          group
      >
        <v-btn
            :value="true"
            id="directed-type-graph"
        >

          <v-icon>moving</v-icon>
        </v-btn>

        <v-btn
            :value="false"
            id="undirected-type-graph">
          <v-icon>show_chart</v-icon>
        </v-btn>
      </v-btn-toggle>
      <v-switch
          v-model="graph"
          label="Граф заранее построен"></v-switch>
    </v-card-text>
  </v-card>
</template>

<script>
import {mapActions, mapMutations} from "vuex";

export default {
  name: "TaskDataCard",
  data() {
    return {
      graph: false,
      valid: true,
      taskName: '',
      taskCategory: '',
      graphType: true
    }
  },
  watch: {
    graph: 'changeGraphEnable',
    graphType: 'changeGraphType'
  },
  methods: {
    ...mapMutations([
      'tasks/editCurrentTaskGraphEnableMutation',
      'tasks/editCurrentTaskGraphTypeMutation'
    ]),
    changeGraphEnable() {
      this['tasks/editCurrentTaskGraphEnableMutation'](this.graph)
    },
    changeGraphType() {
      this['tasks/editCurrentTaskGraphTypeMutation'](this.graphType)
    }
  }
}
</script>

<style scoped>

</style>