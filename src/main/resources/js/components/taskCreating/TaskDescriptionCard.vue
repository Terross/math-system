<template>
  <v-card>
    <v-card-title>Описание задачи</v-card-title>
    <v-card-text style="white-space: pre-line">{{edit ? description : computedDescription}}</v-card-text>
    <v-card-actions>
      <v-btn color="primary"
             dark
             @click="dialog=true">
        Изменить описание
      </v-btn>
    </v-card-actions>
    <v-dialog
        v-model="dialog"
        persistent
    >
      <v-card>
        <v-card-title>
          <span class="text-h5">Описание задачи</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-textarea
                :value="description"
                v-model="description"
                auto-grow
                filled>

            </v-textarea>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              @click="editDescription"
          >
            Сохранить и закрыть
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
import {mapMutations, mapGetters} from "vuex";
export default {
  name: "TaskDescriptionCard",
  data() {
    return {
      dialog: false,
      description: this['tasks/generatedDescription'](),
      edit: false
    }
  },
  methods: {
    ...mapMutations(['tasks/editCurrentTaskDescriptionMutation']),
    ...mapGetters(['tasks/generatedDescription']),
    editDescription() {
      this['tasks/editCurrentTaskDescriptionMutation'](this.description)
      this.edit = true
      this.dialog = false
    }
  },
  computed: {
    currentTask() {
      return this.$store.state.tasks.currentTask
    },
    computedDescription() {
        return this['tasks/generatedDescription']()
    }
  },
  beforeMount() {
    this['tasks/editCurrentTaskDescriptionMutation'](this.description)
  }
}
</script>

<style scoped>

</style>