<template>
  <v-card>
    <v-card-title>Описание задачи</v-card-title>
    <v-card-text style="white-space: pre-line">{{computedDescription}}</v-card-text>
    <v-card-actions>
      <v-btn color="primary"
             dark
             small
             @click="dialog = true">
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
                :value="computedDescription"
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
import {mapMutations} from "vuex";
export default {
  name: "TaskDescriptionCard",
  data() {
    return {
      dialog: false,
      description: ''
    }
  },
  methods: {
    ...mapMutations(['tasks/editCurrentTaskDescriptionMutation']),
    editDescription() {
      this['tasks/editCurrentTaskDescriptionMutation'](this.description)
      this.dialog = false
    }
  },
  computed: {
    currentTask() {
      return this.$store.state.tasks.currentTask
    },
    computedDescription() {
      let text = 'Постройте ' + (this.currentTask.graphDirect ? 'ориентированный': 'неориентированный') +
          ' удовлетворяющий следующим условиям:' + '\n'
      this.currentTask.plugins.forEach(plugin => {
        const pluginType = plugin.plugin.pluginType
        switch (pluginType) {
          case 'CHARACTERISTIC':
            text = text + plugin.plugin.description + ' ' + plugin.value + '\n'
            break
          case 'PROPERTY':
            text = text + plugin.plugin.description + ' ' + plugin.value ? 'выполнено' : 'невыполнено' + '\n'
            break
          default:
            break
        }
      })
      return text
    }
  }

}
</script>

<style scoped>

</style>