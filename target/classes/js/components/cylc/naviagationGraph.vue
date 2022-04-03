<template>
  <v-card id="edit-panel">
    <v-row no-gutters>
      <v-col>
        <v-btn-toggle
            color="secondary"
            mandatory
            v-model="editType"
            group>
          <v-btn
              dark
              @click="moveClick"
              id="graphModeMove"
              value="move"
          >
            <v-icon  dark>open_with</v-icon>
          </v-btn>
          <v-btn
              v-if="permission.draw"
              dark
              @click="drawClick"
              id="graphModeDraw"
              value="draw"
          >
            <v-icon dark>gesture</v-icon>
          </v-btn>

          <v-btn
              v-if="permission.edit"
              dark
              @click="editClick"
              id="graphModeEdit"
              value="edit"
          >
            <v-icon dark>edit</v-icon>
          </v-btn>

          <v-btn
              v-if="permission.remove"
              dark
              value="remove"
              @click="removeClick"
              id="graphModeRemove"
          >
            <v-icon dark>delete</v-icon>
          </v-btn>
        </v-btn-toggle>
      </v-col>
      <v-col>
        <v-menu offset-y class = "mx-4">
          <template v-slot:activator="{ on, attrs }" >
            <v-btn
                color="secondary"
                text
                v-bind="attrs"
                v-on="on"
                id="layouts"
                class="ma-2"
            >
              Отображение графа
            </v-btn>
          </template>
          <v-list color="primary">
            <v-list-item
            >
              <v-btn
                  text
                  color="secondary"
                  dark
                  id="zoom"
              >Zoom
                <v-icon dark>center_focus_strong
                </v-icon>
              </v-btn>
            </v-list-item>
            <v-list-item
            >
              <v-btn
                  text
                  color="secondary"
                  dark
                  id="random-layout"
              >Random
                <v-icon dark>shuffle</v-icon>
              </v-btn>
            </v-list-item>
            <v-list-item
            >
              <v-btn
                  text
                  color="secondary"
                  dark
                  id="circle-layout"
              >circle
                <v-icon dark>motion_photos_on</v-icon>
              </v-btn>
            </v-list-item>
            <v-list-item
            >
              <v-btn
                  text
                  color="secondary"
                  dark
                  id="cola-layout"
              >components
                <v-icon dark>group_work</v-icon>
              </v-btn>
            </v-list-item>
            <v-list-item
            >
              <v-btn
                  text
                  color="secondary"
                  dark
                  id="tree-layout"
              >tree
                <v-icon dark>account_tree</v-icon>
              </v-btn>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-col>
      <v-col>
        <v-btn text
               color="secondary"
               dark
               id="download-graph"
               class="ma-2"
               @click="saveFile">
          Скачать граф файлом
        </v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import {mapMutations} from "vuex";
import {saveAS} from 'file-saver'

export default {
  name: "navigationGraph",
  data() {
    return {
      editType: this.$store.state.currentGraph.editType
    }
  },

  computed: {
    direct() {
      return this.$store.state.tasks.currentTask.graphDirect
    },
    graphInfo() {
      return this.$store.state.currentGraph
    },
    permission() {
      return this.$store.state.tasks.currentTask.permission
    }
  },
  methods: {
    ...mapMutations([
        'currentGraph/changeEditTypeMutation',
        'currentGraph/changeDirectTypeMutation'
    ]),
    async saveFile() {
      let file = this.direct? "DIRECTED": "UNDIRECTED"
      file = file + ' ' + this.graphInfo.vertexCount + '\n'
      file = file + ' ' + this.graphInfo.edgeCount +'\n'
      const vertexList = this.graphInfo.vertexList
      const edgeList = this.graphInfo.edgeList

      vertexList.forEach(vertex => {
        file = file + vertex.id + ' ' + vertex.color + ' ' + vertex.weight + ' ' +
            vertex.label + ' ' + vertex.xCoordinate + ' ' + vertex.yCoordinate + '\n'
      })

      edgeList.forEach(edge => {
        file = file + edge.fromV + ' ' + edge.toV + ' ' + edge.color + ' ' +
            edge.weight + ' ' + edge.label + ' ' + '\n'
      })

      const blob = new Blob([file], {type: "text/plain;charset=utf-8"});
      saveAs(blob, "graph.txt");
    },
    moveClick() {
      this['currentGraph/changeEditTypeMutation']('move')
    },
    editClick() {
      this['currentGraph/changeEditTypeMutation']('edit')
    },
    removeClick() {
      this['currentGraph/changeEditTypeMutation']('remove')
    },
    drawClick() {
      this['currentGraph/changeEditTypeMutation']('draw')
    }
  }
}
</script>

<style scoped>
#edit-panel {
  background-color: #42A5F5;
  width: 100%;
}
</style>