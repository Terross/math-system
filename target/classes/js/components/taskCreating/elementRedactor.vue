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
            <v-col
            >
              <v-text-field
                  label="Вес (для ребер)"
                  v-model="elementData.weight"
                  :rules="[v => isFinite(v)]"
              ></v-text-field>
            </v-col>
            <v-col
            >
              <v-select
                  :items="['red', 'pink', 'blue', 'green','yellow', 'brown', 'gray', '-']"
                  label="Цвет"
                  v-model="elementData.color"

              ></v-select>
            </v-col>
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
      elementData: this.$store.state.constructorGraph.elementData
    }
  },
  watch: {
    elementData() {
      this['constructorGraph/changeElementDataMutation'](this.elementData)
    }
  },
  methods: {
    ...mapMutations['constructorGraph/changeElementDataMutation']
  }
}
</script>

<style scoped>

</style>