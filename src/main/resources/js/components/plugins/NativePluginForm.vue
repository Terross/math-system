<template>
  <v-row justify="center">
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
              <v-form
                  ref="form"
                  v-model="valid"
                  lazy-validation
              >
                <v-text-field
                    v-model="pluginName"
                    label="Название плагина"
                    :rules="[v => !!v || 'Требуется название плагина']"
                    required
                ></v-text-field>
                <v-text-field
                    v-model="pluginDescription"
                    :rules="[v => !!v || 'Требуется название плагина']"
                    label="Описание плагина"
                    required
                ></v-text-field>
                <v-text-field
                    v-model="pluginFileName"
                    :rules="[v => !!v || 'Требуется название плагина']"
                    label="Название файла"
                    hint="С типом файла .java"
                    required
                ></v-text-field>
                <v-select
                    v-model="pluginType"
                    :rules="[v => !!v || 'Требуется название плагина']"
                    :items="['Свойство', 'Характеристика']"
                    label="Тип плагина"
                    required
                ></v-select>
                <v-select
                    v-model="graphType"
                    :rules="[v => !!v || 'Требуется название плагина']"
                    :items="['Ориентированный', 'Неориентированный', 'Любой']"
                    label="Тип графа"
                    required
                ></v-select>
              </v-form>
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
              :disabled="!valid"
              color="primary"
              @click="savePlugin"
          >
            Сохранить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import {HTTP} from "../../axios/http-common";
import {mapMutations} from "vuex";

export default {
  name: "NativePluginForm",
  props: {
    dialog: Boolean
  },
  data() {
    return {
      valid: true,
      isOpen: this.dialog,
      pluginName: '',
      pluginDescription: '',
      pluginFileName: '',
      pluginType: '',
      graphType: ''
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
    ...mapMutations(['plugins/addPluginMutation']),
    openDialog() {
      this.isOpen = this.dialog
    },
    closeDialog() {
      this.$emit('close-dialog')
    },
    savePlugin() {
      const pt = this.pluginType === 'Свойство' ? "PROPERTY" : "CHARACTERISTIC"
      const gt = this.graphType === 'Ориентированный' ? "DIRECTED" :
                                    this.graphType === "Неориентированный" ? "UNDIRECTED": "COMMON"
      const data = {
        "name": this.pluginName,
        "description": this.pluginDescription,
        "pluginType": pt,
        "graphType": gt,
        "fileName": this.pluginFileName,
        "nativeRealization": true,
        "authorEmail": this.$store.state.profile.profile.email
      }
      this.$http
          .post('/api/v1/all/plugin/native-plugin', data, {
            headers: {
              'Authorization' : "Bearer " + this.token
            }
          })
          .then(response => {
            this['plugins/addPluginMutation'](response.data)
            this.$emit('close-dialog')
          })
          .catch(e => console.log(e))
    }
  }
}
</script>

<style scoped>

</style>