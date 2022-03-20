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
              @click="saveNewElementData(selectedElement.elementType, selectedElement.name)"
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
              (selectedElement.elementType === 'vertex' ? "Редактор вершин" : "Редактор ребер") }}</span>
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

import 'cytoscape-context-menus/cytoscape-context-menus.css';
import cola from 'cytoscape-cola'
import dagre from 'cytoscape-dagre';
import graphStyle from "./graphStyle.js";
import colorDisk from "./colorDisk.js";


export default {
  name: "network",
  data() {
    return {
      edgeName: 0,
      menu: false,
      message: false,
      hints: true,
      dialog: false,
      info: false,
      selectedElement: {
        "label" : '',
        "weight" : ''
      },
      reg: this.$store.state.constructorGraph.reg,
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
      return this.$store.state.constructorGraph.editType
    },
    direct() {
      return this.currentTask.graphDirect
    },
    config() {
      return {
        layout: {
          name: 'circle'
        },
        wheelSensitivity: 0.5,
        elements: this.configElements
      }
    },
    currentTask() {
      return this.$store.state.tasks.currentTask
    },
    graphInfo() {
      return this.$store.state.constructorGraph
    }
  },
  methods: {
    ...mapMutations([
        'constructorGraph/registrationMutation',
        'constructorGraph/addNodeMutation',
        'constructorGraph/addEdgeMutation',
        'constructorGraph/removeEdgeMutation',
        'constructorGraph/removeNodeMutation',
        'constructorGraph/updateEdgeColorMutation',
        'constructorGraph/updateEdgeWeightMutation',
        'constructorGraph/updateNodeColorMutation',
        'constructorGraph/changeEdgeData',
        'constructorGraph/changeVertexData',
        'constructorGraph/changeVertexCoordinateMutation'
    ]),
    ...mapGetters([
        'tasks/findGraphDataByTaskId',
        'tasks/findTaskById',
        'constructorGraph/findEdgeById',
        'constructorGraph/findVertexById'
    ]),
    preConfig(cytoscape) {
      if (!this.reg) {
        cytoscape.use(edgehandles)
        cytoscape.use(cola)
        cytoscape.use(dagre)
        cytoscape.use(cxtmenu)
        cytoscape.use(popper)
        this['constructorGraph/registrationMutation']()
      }
    },
    changeElemColor(ele, color) {
      ele.style('background-color', color)
      if (ele.group().toString() === 'edges') {
        ele.style('line-color', color)
        ele.style('target-arrow-color', color)
        const source = ele.data().source
        const target = ele.data().target

        this['constructorGraph/updateEdgeColorMutation'](
            {
              "fromV": ele.data().find(item => item.id === source).name,
              "toV": ele.data().find(item => item.id === target).name,
              "color": color
            }
        )
      } else {
        this['constructorGraph/updateNodeColorMutation'](
            {
              "name" : ele.data().name,
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
        this['constructorGraph/addEdgeMutation']({
          "name":  this.edgeName,
          "color" : 'gray',
          "fromV" : sourceNode.data().name,
          "toV" : targetNode.data().name,
          "weight" : null,
          "label": null
        })
        this.edgeName++
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
          this.showDetails(event)
          break
        case 'remove':
          this.removeElem(event)
          break
        case 'edit':
          this.editElem(event)
          break
        case 'details':
          this.showDetails(event)
          break;
      }
    },
    showDetails(event) {
      console.log(123)
      let {target} = event
      let {cy} = event
      if (event.target !== event.cy) {
        if (target.group().toString() === 'nodes') {
          this.selectedElement.weight =
              this.$store.getters["constructorGraph/findVertexById"](target.data().name).weight
          this.selectedElement.label =
              this.$store.getters["constructorGraph/findVertexById"](target.data().name).label
          this.selectedElement.name = target.data().name
          this.selectedElement.elementType = "vertex"
          this.info = true
        } else {
          if (target.group().toString() === 'edges') {

            this.selectedElement.weight =
                this.$store.getters["constructorGraph/findEdgeById"](target.data().name).weight
            this.selectedElement.label =
                this.$store.getters["constructorGraph/findEdgeById"](target.data().name).label
            this.selectedElement.elementType = "edge"
            this.selectedElement.name = target.data().name
            this.selectedElement.edge = target
            this.selectedElement.target = target
            this.info = true
          }
        }
      }
    },
    editElem(event) {
      let {target} = event
      let {cy} = event
      if (event.target !== event.cy) {
        if (target.group().toString() === 'nodes') {
          this.selectedElement.weight =
              this["constructorGraph/findVertexById"](target.data().name).weight
          this.selectedElement.label =
              this["constructorGraph/findVertexById"](target.data().name).label
          this.selectedElement.name = target.data().name
          this.selectedElement.elementType = "vertex"
          this.dialog = true
        } else {
          if (target.group().toString() === 'edges') {
            this.selectedElement.weight =
                this["constructorGraph/findEdgeById"](target.data().name).weight
            this.selectedElement.label =
                this["constructorGraph/findEdgeById"](target.data().name).label
            this.selectedElement.elementType = "edge"
            this.selectedElement.name = target.data().name
            this.selectedElement.target = target
            this.dialog = true
          }
        }
        this.selectedElement.elem = target
      }
    },
    dragOverEvent(event) {
      if (event.target !== event.cy && event.target.group().toString() === 'nodes') {
        this['constructorGraph/changeVertexCoordinateMutation']({
          name: event.target.data().name,
          xCoordinate: event.position.x,
          yCoordinate: event.position.y
        })
      }

    },
    addNode(event) {
      let {position} = event
      if (event.target === event.cy) {
        const newName = this.$store.getters["constructorGraph/nextVertexName"]
        const newIndex = this.$store.getters["constructorGraph/nextVertexIndex"]
        event.cy.add({
          group: 'nodes',
          data: {
            name: newName,
            color: 'gray'
          },
          classes: 'multiline-manual',
          position: {x: position.x, y: position.y}
        })
        this['constructorGraph/addNodeMutation'](
            {
              "xCoordinate": position.x,
              "yCoordinate": position.y,
              "index": newIndex,
              "name": newName,
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
          this['constructorGraph/removeEdgeMutation']({
            "name" : target.data().name,
            "fromV" : ver.find(item => item.id === sourceV).name,
            "toV" : ver.find(item => item.id === targetV).name
          })
        } else {
          let index = target.data().name
          for (let i = 0; i < cy.nodes().length; i++) {
            let node = cy.nodes()[i]
            if (node.data().name > index) {
              node.data('name', node.data().name - 1)
            }
          }
          this['constructorGraph/removeNodeMutation']({
            "name" : target.data().name
          })
        }
      }
    },
    saveNewElementData(elementType, name) {
      if (!isNaN(Number(this.selectedElement.weight))) {
        if (elementType === 'edge') {
          this['constructorGraph/changeEdgeData']({
            "name" : name,
            "label" : this.selectedElement.label,
            "weight" : this.selectedElement.weight
          })
        } else {
          if (elementType === 'vertex')  {
            this['constructorGraph/changeVertexData']({
              "name" : name,
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