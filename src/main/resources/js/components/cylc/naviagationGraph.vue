<template>
  <v-card id="edit-panel">
    <v-row justify="start"  no-gutters>

      <v-col>
        <v-btn-toggle
                      color="primary"
                      dense
                      group>
          <v-btn
              dark
              class="ma-4"
              @click="moveClick"
              :disabled="this.editType === 'move'"
              id="graphModeMove"
          >
            Move
            <v-icon  dark>open_with</v-icon>
          </v-btn>
          <v-btn
              dark
              class="ma-4"
              :disabled="this.editType === 'edit' ||
               (!this.permission.edit && this.networkType !== 'constructor')"
              @click="editClick"
              id="graphModeEdit"
          >
            Edit
            <v-icon dark>edit</v-icon>
          </v-btn>

          <v-btn
              dark
              class="ma-4"
              :disabled="this.editType === 'remove' ||
              (!this.permission.remove &&  this.networkType !== 'constructor')"
              @click="removeClick"
              id="graphModeRemove"
          >
            Remove
            <v-icon dark>delete</v-icon>
          </v-btn>
        </v-btn-toggle>
      </v-col>

      <v-col>
        <v-menu offset-y class = "mx-4">
          <template v-slot:activator="{ on, attrs }" >
            <v-btn
                color="indigo lighten-4"
                text
                v-bind="attrs"
                v-on="on"
                id="testId2"
                class = "ma-4 align-center"
            >
              Расстановка графа
            </v-btn>
          </template>
          <v-list color="indigo lighten-1">
            <v-list-item
            >
              <v-btn
                  text
                  color="indigo lighten-4"
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
                  color="indigo lighten-4"
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
                  color="indigo lighten-4"
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
                  color="indigo lighten-4"
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
               color="indigo lighten-4"
               dark
               id="download-graph"
               class="ma-4"
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
  computed: {
    changeColor() {
      return this.$store.state.constructorGraph.changeColor
    },
    editType() {
      return this.$store.state.constructorGraph.editType
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
        file = file + vertexList[i].name + ' ' + vertexList[i].color +'\n'
      }

      for (let i = 0; i < vertexList.length;i++) {
        for (let j = 0; j < vertexList[i].outgoingEdges.length; j++) {
          let edge = vertexList[i].outgoingEdges[j]
          let fromVertex = vertexList.filter(item => item.name.toString() === edge.fromV.toString())[0]
          let toVertex = vertexList.filter(item => item.name.toString() === edge.toV.toString())[0]
          file = file +
              fromVertex.name +  ' ' + toVertex.name + ' '
              + (edge.weight === ''? 0 : edge.weight) + ' ' + edge.color + '\n'
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
  background-color: #5c6bc0;
  width: 100%;
}
</style>