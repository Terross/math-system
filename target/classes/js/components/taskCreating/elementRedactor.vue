<template>
<v-container>
  <v-col>
    <v-card class="mx-auto"
            min-width="344">
      <v-card-title>
        <span class="text-h5">Редактор элемента графа</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-checkbox
                :disabled="!permission.weight &&  this.networkType !== 'constructor'"
                v-model="elementData.enableWeight"
            ></v-checkbox>
            <v-text-field
                :disabled="!permission.weight &&  this.networkType !== 'constructor'"
                label="Вес (для ребер)"
                v-model="elementData.weight"
                :rules="[v => isFinite(v)]"
            ></v-text-field>
          </v-row>
          <v-row>
            <v-checkbox
                v-model="elementData.enableColor"
                :disabled="!permission.color &&  this.networkType !== 'constructor'"
            ></v-checkbox>
            <v-select
                :disabled="!permission.color &&  this.networkType !== 'constructor'"
                :items="['red', 'pink', 'blue', 'green','yellow', 'brown', 'gray']"
                label="Цвет"
                v-model="elementData.color"

            ></v-select>
          </v-row>
        </v-container>
      </v-card-text>
    </v-card>
  </v-col>
</v-container>
</template>

<script>
import {mapMutations} from "vuex";

export default {
  name: "elementRedactor",
  data() {
    return {
      elementData: this.$store.state.constructorGraph.elementData,
      networkType: this.$route.path.includes('/task/') ? 'task' : 'constructor'
    }
  },
  watch: {
    elementData() {
      this['constructorGraph/changeElementDataMutation'](this.elementData)
    }
  },
  methods: {
    ...mapMutations['constructorGraph/changeElementDataMutation']
  },
  computed: {
    permission() {
      return this.$store.state.constructorGraph.permission
    }
  }
}
</script>

<style scoped>

</style>