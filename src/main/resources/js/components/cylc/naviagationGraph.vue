<template>
  <v-card id="edit-panel">
    <v-row justify="start"  no-gutters>
      <v-col>
        <v-btn
            color="white"
            text
            class="ma-2 white--text"
            @click="moveClick"
            :disabled="this.editType === 'move'"
            id="graphModeMove"
        >
          Move
          <v-icon right dark>open_with</v-icon>
        </v-btn>
        <v-btn
            color="white"
            text
            class="ma-2 white--text"
            :disabled="this.editType === 'edit'"
            @click="editClick"
            id="graphModeEdit"
        >
          Edit
          <v-icon right dark>edit</v-icon>
        </v-btn>

        <v-btn
            color="white"
            text
            class="ma-2 white--text"
            :disabled="this.editType === 'remove'"
            @click="removeClick"
            id="graphModeRemove"
        >
          Remove
          <v-icon right dark>delete</v-icon>
        </v-btn>
      </v-col>
      <v-col>
        <v-btn
            color="white"
            text
            class="ma-2 white--text"
            :disabled="this.direct === true"
            @click="directedTypeClick"
            id="directed-type-graph"
        >
           Ориентированный граф
          <v-icon right dark>moving</v-icon>
        </v-btn>
        <v-btn
            color="white"
            text
            class="ma-2 white--text"
            :disabled="this.direct === false"
            @click="undirectedTypeClick"
            id="undirected-type-graph"
        >
          Неориентированный граф
          <v-icon right dark>show_chart</v-icon>
        </v-btn>
      </v-col>
    </v-row>

  </v-card>
</template>

<script>
import {mapMutations} from "vuex";

export default {
  name: "navigationGraph",
  props: {
    networkType: String
  },
  computed: {
    changeColor() {
      return this.$store.state.constructorGraph.changeColor
    },
    editType() {
      return this.$store.state.constructorGraph.editType
    },
    changeLabel() {
      return  this.$store.state.constructorGraph.changeLabel
    },
    direct() {
      return this.$store.state.constructorGraph.direct
    }
  },
  methods: {
    ...mapMutations([
        'constructorGraph/changeEditTypeMutation',
        'constructorGraph/changeDirectTypeMutation'
    ]),
    moveClick() {
      this['constructorGraph/changeEditTypeMutation']('move')
    },
    editClick() {
      this['constructorGraph/changeEditTypeMutation']('edit')
    },
    removeClick() {
      this['constructorGraph/changeEditTypeMutation']('remove')
    },
    directedTypeClick() {
      this['constructorGraph/changeDirectTypeMutation'](true)
    },
    undirectedTypeClick() {
      this['constructorGraph/changeDirectTypeMutation'](false)
    }
  }
}
</script>

<style scoped>
#edit-panel {
  background-color: #5c6bc0;
  width: 100%;
}
</style>