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
  props: {
    networkType: String
  },
  data() {
    return {
      editType: this.$store.state.constructorGraph.editType
    }
  },

  computed: {
    changeColor() {
      return this.$store.state.constructorGraph.changeColor
    },
    changeLabel() {
      return  this.$store.state.constructorGraph.changeLabel
    },
    direct() {
      return this.$store.state.constructorGraph.direct
    },
    graphInfo() {
      return this.$store.state.constructorGraph
    },
    permission() {
      return this.$store.state.constructorGraph.permission
    }
  },
  methods: {
    ...mapMutations([
        'constructorGraph/changeEditTypeMutation',
        'constructorGraph/changeDirectTypeMutation'
    ]),
    async saveFile() {
      let file = this.graphInfo.vertexCount
      file = file + ' ' + this.graphInfo.edgeCount +'\n'
      let vertexList = this.graphInfo.constructorGraph
      for (let i = 0; i < vertexList.length; i++) {
        file =
            file +
            vertexList[i].name + ' ' +
            vertexList[i].color + ' ' +
            vertexList[i].weight + ' ' +
            (vertexList[i].label === '' ? 'null' : vertexList[i].label) + '\n'
      }

      for (let i = 0; i < vertexList.length;i++) {
        for (let j = 0; j < vertexList[i].outgoingEdges.length; j++) {
          let edge = vertexList[i].outgoingEdges[j]
          let fromVertex = vertexList.filter(item => item.name.toString() === edge.fromV.toString())[0]
          let toVertex = vertexList.filter(item => item.name.toString() === edge.toV.toString())[0]
          file =
              file +
              fromVertex.name +  ' ' +
              toVertex.name + ' ' +
              edge.weight + ' ' +
              edge.color + ' ' +
              (edge.label === '' ? 'null' : edge.label)  + ' ' +
              edge.name + '\n'
        }
      }
      var blob = new Blob([file], {type: "text/plain;charset=utf-8"});
      saveAs(blob, "graph.txt");
    },
    moveClick() {
      this['constructorGraph/changeEditTypeMutation']('move')
    },
    editClick() {
      this['constructorGraph/changeEditTypeMutation']('edit')
    },
    removeClick() {
      this['constructorGraph/changeEditTypeMutation']('remove')
    },
    drawClick() {
      this['constructorGraph/changeEditTypeMutation']('draw')
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
  background-color: #673AB7;
  width: 100%;
}
</style>