<template>

  <cytoscape ref="cy"
             id="cy"
             :config="config"
             :afterCreated="afterCreated"
             :preConfig="preConfig"
             v-on:click="leftClick"
             v-on:tapend="dragOverEvent"
             style="height: 75vh"
  >
    <v-dialog
        v-model="dialog"
        persistent
        max-width="290"
    >
      <v-card>
        <v-card-title>
          <span class="text-h5">{{ selectedElement === null ? '' :
              (selectedElement.elementType === 'vertex' ? "Редактор вершин" : "Редактор ребер") }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
              >
                <v-text-field
                    v-model="selectedElement.label"
                    :value = "selectedElement.label"
                    label="Метка"
                    required
                ></v-text-field>
                <v-text-field
                    v-model="selectedElement.weight"
                    :value = "selectedElement.weight"
                    :rules="[v => !isNaN(Number(v)) || 'Требуется целое число']"
                    required
                    label="Вес"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              dark
              @click="dialog = false"
          >
            Закрыть
          </v-btn>
          <v-btn
              id="saveNewData"
              color="primary"
              dark
              @click="saveNewElementData(selectedElement.elementType, selectedElement.id)"
          >
            Сохранить
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog
        v-model="info"
        persistent
        max-width="290"
    >
      <v-card>
        <v-card-title>
          <span class="text-h5">{{ selectedElement === null ? '' :
              (selectedElement.elementType === 'vertexProjectio' ? "Редактор вершин" : "Редактор ребер") }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
              >
                <p class="text-h7">{{ 'Метка: '.concat(selectedElement.label) }}</p>

                <p class="text-h7">{{ 'Вес: '.concat(selectedElement.weight) }}</p>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="blue darken-1"
              text
              dark
              @click="info = false"
          >
            Закрыть
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </cytoscape>
</template>

<script>
import {mapActions, mapGetters, mapMutations} from 'vuex'
import edgehandles from 'cytoscape-edgehandles'
import cxtmenu from 'cytoscape-cxtmenu';
import popper from 'cytoscape-popper';
import { uuid } from 'vue-uuid';

import 'cytoscape-context-menus/cytoscape-context-menus.css';
import cola from 'cytoscape-cola'
import dagre from 'cytoscape-dagre';
import graphStyle from "./graphStyle.js";
import colorDisk from "./colorDisk.js";


export default {
  name: "network",
  data() {
    return {
      menu: false,
      message: false,
      hints: true,
      dialog: false,
      info: false,
      selectedElement: {
        "label" : '',
        "weight" : ''
      },
      reg: this.$store.state.currentGraph.reg,
      directedStyle: graphStyle.directed,
      undirectedStyle: graphStyle.undirected
    }
  },
  props: {
    configElements: Array,
    graphType: Boolean
  },
  computed: {
    editType() {
      return this.$store.state.currentGraph.editType
    },
    direct() {
      return this.currentTask.graphDirect
    },
    config() {
      return {
        layout: {
          name: 'preset'
        },
        wheelSensitivity: 0.5,
        elements: this.configElements
      }
    },
    currentTask() {
      return this.$store.state.tasks.currentTask
    },
    graphInfo() {
      return this.$store.state.currentGraph
    }
  },
  methods: {
    ...mapMutations([
        'currentGraph/registrationMutation',
        'currentGraph/addNodeMutation',
        'currentGraph/addEdgeMutation',
        'currentGraph/removeEdgeMutation',
        'currentGraph/removeNodeMutation',
        'currentGraph/updateEdgeColorMutation',
        'currentGraph/updateEdgeWeightMutation',
        'currentGraph/updateNodeColorMutation',
        'currentGraph/changeEdgeData',
        'currentGraph/changeVertexData',
        'currentGraph/changeVertexCoordinateMutation'
    ]),
    ...mapGetters([
        'tasks/findGraphDataByTaskId',
        'tasks/findTaskById',
        'currentGraph/findEdgeById',
        'currentGraph/findVertexById'
    ]),
    preConfig(cytoscape) {
      if (!this.reg) {
        cytoscape.use(edgehandles)
        cytoscape.use(cola)
        cytoscape.use(dagre)
        cytoscape.use(cxtmenu)
        cytoscape.use(popper)
        this['currentGraph/registrationMutation']()
      }
    },
    changeElemColor(ele, color) {
      ele.style('background-color', color)
      if (ele.group().toString() === 'edges') {
        ele.style('line-color', color)
        ele.style('target-arrow-color', color)
        const source = ele.data().source
        const target = ele.data().target
        this['currentGraph/updateEdgeColorMutation'](
            {
              "fromV": source,
              "toV": target,
              "color": color
            }
        )
      } else {
        this['currentGraph/updateNodeColorMutation'](
            {
              "id" : ele.data().id,
              "color": color
            }
        )
      }
    },
    afterCreated(cy) {
      let eh = cy.edgehandles()
      const directedStyle = this.directedStyle
      const undirectedStyle = this.undirectedStyle

      if (this.graphInfo.editType === 'edit') {
        eh.enableDrawMode()
      }

      if (this.currentTask.permission.color) {
        cy.cxtmenu({
          menuRadius: function(ele){ return 75; },
          selector: 'node, edge',
          outsideMenuCancel: 10,
          commands: colorDisk(this),
          activeFillColor: 'rgba(103,58,183,0.25)'
        });
      }

      if (this.currentTask.graphDirect) {
        cy.style().fromJson(directedStyle).update()
      } else {
        cy.style().fromJson(undirectedStyle).update()
      }

      cy.on('ehcomplete', (event, sourceNode, targetNode, addedEdge) => {
        this['currentGraph/addEdgeMutation']({
          "id" : addedEdge.data().id,
          "color" : 'gray',
          "fromV" : sourceNode.data().id,
          "toV" : targetNode.data().id,
          "weight" : null,
          "label": null
        })
      });

      document.getElementById('layouts').addEventListener('click', function () {
        document.querySelector('#circle-layout').addEventListener('click', function() {
          cy.layout({ name: 'circle' }).run()
        })
        document.querySelector('#tree-layout').addEventListener('click', function() {
          cy.layout({ name: 'dagre' }).run()
        })
        document.querySelector('#cola-layout').addEventListener('click', function() {
          cy.layout({ name: 'cola' }).run()
        })
        document.querySelector('#random-layout').addEventListener('click', function() {
          cy.layout({ name: 'random' }).run()
        })
        document.querySelector('#zoom').addEventListener('click', function () {
          cy.fit()
        })
      })
      if (document.querySelector('#graphModeDraw') != null) {
        document.querySelector('#graphModeDraw').addEventListener('click', function() {
          eh.enableDrawMode()
        })
      }
      if (document.querySelector('#graphModeRemove') != null) {
        document.querySelector('#graphModeRemove').addEventListener('click', function() {
          eh.disableDrawMode()
        })
      }
      if (document.querySelector('#graphModeMove') != null) {
        document.querySelector('#graphModeMove').addEventListener('click', function() {
          eh.disableDrawMode()
        })
      }
      if (document.querySelector('#graphModeEdit') != null) {
        document.querySelector('#graphModeEdit').addEventListener('click', function() {
          eh.disableDrawMode()
        })
      }
      if (document.querySelector('#directed-type-graph') != null) {
        document.querySelector('#directed-type-graph').addEventListener('click', function() {
          cy.style().fromJson(directedStyle).update()
        })
        document.querySelector('#undirected-type-graph').addEventListener('click', function() {
          cy.style().fromJson(undirectedStyle).update()
        })
      }
    },
    leftClick(event) {
      switch (this.editType) {
        case 'draw':
          this.addNode(event)
          break
        case 'move':
          break
        case 'remove':
          this.removeElem(event)
          break
        case 'edit':
          this.editElem(event)
          break
      }
    },
    editElem(event) {
      let {target} = event
      let {cy} = event
      if (event.target !== event.cy) {
        if (target.group().toString() === 'nodes') {
          this.selectedElement.weight =
              this["currentGraph/findVertexById"](target.data().id).weight
          this.selectedElement.label =
              this["currentGraph/findVertexById"](target.data().id).label
          this.selectedElement.id = target.data().id
          this.selectedElement.elementType = "vertex"
          this.dialog = true
        } else {
          if (target.group().toString() === 'edges') {
            this.selectedElement.weight =
                this["currentGraph/findEdgeById"](target.data().id).weight
            this.selectedElement.label =
                this["currentGraph/findEdgeById"](target.data().id).label
            this.selectedElement.elementType = "edge"
            this.selectedElement.name = target.data().id
            this.selectedElement.target = target
            this.dialog = true
          }
        }
        this.selectedElement.elem = target
      }
    },
    dragOverEvent(event) {
      if (event.target !== event.cy && event.target.group().toString() === 'nodes') {
        this['currentGraph/changeVertexCoordinateMutation']({
          "id": event.target.data().id,
          "xCoordinate": event.position.x,
          "yCoordinate": event.position.y
        })
      }
    },
    addNode(event) {
      let {position} = event
      const uuid4 = uuid.v4()
      if (event.target === event.cy) {
        event.cy.add({
          group: 'nodes',
          data: {
            id: uuid4,
            color: 'gray'
          },
          classes: 'multiline-manual',
          position: {x: position.x, y: position.y}
        })

        this['currentGraph/addNodeMutation'](
            {
              "id": uuid4,
              "xCoordinate": position.x,
              "yCoordinate": position.y,
              "color": 'gray',
              "weight": null,
              "label": null,
              "outgoingEdges": [],
              "incomingEdges": []
            }
        )
      }
    },
    removeElem(event) {
      let {target} = event
      let {cy} = event
      if (target !== cy) {
        cy.remove(target)
        if (target.group().toString() === 'edges') {
          const sourceV = target.data().source
          const targetV = target.data().target
          let ver = []
          cy.nodes().map(item => ver.push(item.data()))
          this['currentGraph/removeEdgeMutation']({
            "name" : target.data().id,
            "fromV" : ver.find(item => item.id === sourceV).id,
            "toV" : ver.find(item => item.id === targetV).id
          })
        } else {
          this['currentGraph/removeNodeMutation']({
            "id" : target.data().id
          })
        }
      }
    },
    saveNewElementData(elementType, id) {
      if (!isNaN(Number(this.selectedElement.weight))) {
        if (elementType === 'edge') {
          this['currentGraph/changeEdgeData']({
            "id" : id,
            "label" : this.selectedElement.label,
            "weight" : this.selectedElement.weight
          })
        } else {
          if (elementType === 'vertex')  {
            this['currentGraph/changeVertexData']({
              "id" : id,
              "label" : this.selectedElement.label,
              "weight" : this.selectedElement.weight
            })
          }
        }
        this.selectedElement.elem.data('label', this.selectedElement.weight + '\n' + this.selectedElement.label)
        this.dialog = false
      }
    },
  }
}
</script>

<style>

#cy {
  width: 100%;
  height: 100%;
  background-color: #e8eef6;
}

#cytoscape-div {
  width: 100%;
  height: 95%;
}


</style>