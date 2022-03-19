<template>
    <v-dialog
        v-model="isOpen"
        persistent
        max-width="600px"
    >
      <v-card>
        <v-card-title>
          <span class="text-h5">Нативный плагин</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-col>
              <v-text-field
                  v-model="pluginName"
                  label="Название плагина"
                  required
              ></v-text-field>
              <v-text-field
                  v-model="pluginDescription"
                  label="Описание плагина"
                  required
              ></v-text-field>
            </v-col>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-btn
              color="primary"
              @click="closeDialog"
          >
            Закрыть
          </v-btn>
          <v-btn
              color="primary"
              @click="savePlugin"
          >
            Сохранить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>

<script>
import {HTTP} from "../../axios/http-common";
import {mapMutations} from "vuex";

export default {
  name: "EditPluginInfo",
  props: {
    dialog: Boolean,
    plugin: Object
  },
  data() {
    return {
      isOpen: this.dialog,
      pluginName: this.plugin.pluginName,
      pluginDescription: this.plugin.pluginDescription
    }
  },
  computed: {
    token() {
      return this.$store.state.profile.profile.jwt
    },
    currentRole() {
      return this.$store.state.profile.profile.role
    }
  },
  watch: {
    dialog: 'openDialog'
  },
  methods: {
    ...mapMutations(['plugins/editPluginMutation']),
    openDialog() {
      this.isOpen = this.dialog
    },
    closeDialog() {
      this.$emit('close-dialog')
    },
    savePlugin() {
      const data = {
        "name": this.pluginName,
        "description": this.pluginDescription
      }
      console.log(data)
      HTTP
          .put(`all/plugin/plugin/${this.plugin.id}`, data,{
            headers: {
              'Authorization' : "Bearer " + this.token
            }
          })
          .then((response) => {
            this['plugins/editPluginMutation'](response.data)
            this.$emit('close-dialog')
          })
          .catch(e => console.log(e))
    }
  }
}
</script>

<style scoped>

</style>