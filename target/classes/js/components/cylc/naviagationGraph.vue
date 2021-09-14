<template>
  <v-card id="edit-panel">
    <v-row justify="start"  no-gutters>
        <v-btn
            color="white"
            text
            class="my-4 white--text"
            @click="moveClick"
            :disabled="moveDisable"
            id="graphModeMove"
        >
          Move
          <v-icon right dark>open_with</v-icon>
        </v-btn>



        <v-btn
            color="white"
            text
            class="my-4 white--text"
            :disabled="editDisable"
            @click="editClick"
            id="graphModeEdit"
        >
          Edit
          <v-icon right dark>edit</v-icon>

        </v-btn>

        <v-btn
            color="white"
            text
            class="my-4 white--text"
            :disabled="colorDisable"
            @click="colorClick"
            id="graphModeColor"
        >
          Color
          <v-icon right dark>palette</v-icon>
        </v-btn>
        <v-btn
            color="white"
            text
            class="my-4 white--text"
            :disabled="removeDisable"
            @click="removeClick"
            id="graphModeRemove"
        >
          Remove
          <v-icon right dark>delete</v-icon>
        </v-btn>

        <v-text-field
            dark
            style="margin-left: 20px"
            v-model="newLabel"
            label="New Label"
            required
            id="elementLabel"
        ></v-text-field>
    </v-row>

  </v-card>
</template>

<script>
export default {
  name: "navigationGraph",
  data() {
    return {
      newLabel: '',
      moveDisable: true,
      editDisable: false,
      colorDisable: false,
      removeDisable: false
    }
  },
  methods: {
    moveClick() {
      this.moveDisable = true
      this.editDisable = false
      this.colorDisable = false
      this.removeDisable = false
    },
    editClick() {
      this.moveDisable = false
      this.editDisable = true
      this.colorDisable = false
      this.removeDisable = false
    },
    colorClick() {
      this.moveDisable = false
      this.editDisable = false
      this.colorDisable = true
      this.removeDisable = false
    },
    removeClick() {
      this.moveDisable = false
      this.editDisable = false
      this.colorDisable = false
      this.removeDisable = true
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