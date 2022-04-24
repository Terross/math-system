<template>
  <v-card
      class="mx-auto"
      min-width="320"
  >
    <EditPluginInfo :dialog="dialog"
                    :plugin="plugin"
                    v-on:close-dialog="dialog=false"></EditPluginInfo>
    <v-card-title>
      {{plugin.name}}
    </v-card-title>
    <v-card-subtitle>
      {{'Имя файла: ' + plugin.fileName}}
      <v-icon v-if="plugin.nativeRealization">done</v-icon>
    </v-card-subtitle>
    <v-card-text class="mx-2">
      <p class="text-h7 text--primary">
        {{plugin.pluginType === 'CHARACTERISTIC' ?  'Характеристика' : 'Свойство'}}
        {{plugin.graphType === 'DIRECTED'  ?  'для ориентированного графа' :
          plugin.graphType === 'UNDIRECTED'?  'для неориентированного графа': 'для любого графа'}}
      </p>
      <div class="text--primary">
        {{plugin.description}}
      </div>
    </v-card-text>
    <v-card-actions>
      <v-row justify="start">
        <v-btn
            v-if="currentEmail===plugin.authorEmail || currentRole==='ADMIN'"
            color="error"
            @click="removePlugin(plugin.id)"
            class="ma-4"
            dark>
          Удалить
        </v-btn>
      </v-row>

    </v-card-actions>
  </v-card>
</template>

<script>
import EditPluginInfo from "./EditPluginInfo.vue";
import { mapMutations } from "vuex";
import {HTTP} from "../../axios/http-common";
export default {
  name: "PluginCard",
  components: {EditPluginInfo},
  data() {
    return {
      dialog: false
    }
  },
  props: {
    plugin: Object
  },
  computed: {
    currentEmail() {
      return this.$store.state.profile.profile.email
    },
    currentRole() {
      return this.$store.state.profile.profile.role
    },
    token() {
      return this.$store.state.profile.profile.jwt
    },
  },
  methods: {
    ...mapMutations(['plugins/removePluginMutation']),
    editPlugin(plugin) {
      this.dialog = true
    },
    removePlugin(id) {
      this.$http
          .delete(`/api/v1/all/plugin/plugin/${id}`, {
            headers: {
              'Authorization' : "Bearer " + this.token
            }
          })
          .then(() => {
            console.log(id)
            this['plugins/removePluginMutation'](id)
          })
          .catch(e => console.log(e))
    }
  }
}
</script>

<style scoped>

</style>