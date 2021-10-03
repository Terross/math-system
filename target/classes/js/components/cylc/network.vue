<template>

  <cytoscape ref="cy"
             id="cy"
             :config="config"
             :afterCreated="afterCreated"
             :preConfig="preConfig"
             v-on:click="leftClick"
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
                    label="Вес"
                ></v-text-field>
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
              @click="dialog = false"
          >
            Закрыть
          </v-btn>
          <v-btn
              id="saveNewData"
              color="blue darken-1"
              text
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
      directedStyle: [
        {
          selector: 'node[name]',
          style: {
            'content': 'data(name)'
          }
        },
        {
          selector: 'node[color]',
          style: {
            'background-color': 'data(color)',
          }
        },
        {
          selector: 'edge',
          style: {
            'curve-style': 'bezier',
            'target-arrow-shape': 'triangle'
          }
        },
        {
          selector: 'edge[color]',
          style: {
            'curve-style': 'bezier',
            'target-arrow-shape': 'triangle',
            'line-color' : 'data(color)',
            'background-color' : 'data(color)',
            'target-arrow-color': 'data(color)',

          }
        },
        {
          selector: 'edge[weight]',
          style: {
            'curve-style': 'bezier',
            'target-arrow-shape': 'triangle',
            'content': 'data(weight)'
          }
        },
        {
          selector: ':selected',
          style: {
            "border-width": 2,
            "border-color": "blue"
          }
        },
        {
          selector: '.eh-handle',
          style: {
            'background-color': 'red',
            'width': 12,
            'height': 12,
            'shape': 'ellipse',
            'overlay-opacity': 0,
            'border-width': 12, // makes the handle easier to hit
            'border-opacity': 0
          }
        },
        {
          selector: '.eh-hover',
          style: {
            'background-color': 'red'
          }
        },
        {
          selector: '.eh-source',
          style: {
            'border-width': 2,
            'border-color': 'red'
          }
        },
        {
          selector: '.eh-target',
          style: {
            'border-width': 2,
            'border-color': 'red'
          }
        },
        {
          selector: '.eh-preview, .eh-ghost-edge',
          style: {
            'background-color': 'red',
            'line-color': 'red',
            'target-arrow-color': 'red',
            'source-arrow-color': 'red'
          }
        },
        {
          selector: '.eh-ghost-edge.eh-preview-active',
          style: {
            'opacity': 0
          }
        }],
      undirectedStyle: [
        {
          selector: 'node[name]',
          style: {
            'content': 'data(name)',
          }
        },
        {
          selector: 'node[color]',
          style: {
            'background-color': 'data(color)',
          }
        },
        {
          selector: 'edge',
          style: {
            'curve-style': 'bezier',
          }
        },
        {
          selector: 'edge[weight]',
          style: {
            'curve-style': 'bezier',
            'content': 'data(weight)',
          }
        },
        {
          selector: ':selected',
          style: {
            "border-width": 2,
            "border-color": "blue"
          }
        },
        {
          selector: '.eh-handle',
          style: {
            'background-color': 'red',
            'width': 12,
            'height': 12,
            'shape': 'ellipse',
            'overlay-opacity': 0,
            'border-width': 12, // makes the handle easier to hit
            'border-opacity': 0
          }
        },
        {
          selector: '.eh-hover',
          style: {
            'background-color': 'red'
          }
        },
        {
          selector: '.eh-source',
          style: {
            'border-width': 2,
            'border-color': 'red'
          }
        },
        {
          selector: '.eh-target',
          style: {
            'border-width': 2,
            'border-color': 'red'
          }
        },
        {
          selector: '.eh-preview, .eh-ghost-edge',
          style: {
            'background-color': 'red',
            'line-color': 'red',
          }
        },
        {
          selector: '.eh-ghost-edge.eh-preview-active',
          style: {
            'opacity': 0
          }
        }]
    }
  },
  props: {
    networkType: String,
    configElements: Array,
    graphType: Boolean
  },
  computed: {
    changeColor() {
      return this.$store.state.constructorGraph.changeColor
    },
    editType() {
      return this.$store.state.constructorGraph.editType
    },
    changeLabel() {
      return this.$store.state.constructorGraph.changeLabel
    },
    direct() {
      return this.$store.state.constructorGraph.direct
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
    elementData() {
      return this.$store.state.constructorGraph.elementData
    },
    testGraph() {
      return this.$store.state.constructorGraph.constructorGraph
    },
    graphInfo() {
      return this.$store.state.constructorGraph
    }
  },
  methods: {
    ...mapMutations([
        'constructorGraph/changeDirectTypeMutation',
        'constructorGraph/changeEditTypeMutation',
        'constructorGraph/registrationMutation',
        'constructorGraph/addNodeMutation',
        'constructorGraph/addEdgeMutation',
        'constructorGraph/removeEdgeMutation',
        'constructorGraph/removeNodeMutation',
        'constructorGraph/updateEdgeColorMutation',
        'constructorGraph/updateEdgeWeightMutation',
        'constructorGraph/updateNodeColorMutation',
        'constructorGraph/initMutation',
        'constructorGraph/cleanGraphMutation',
        'constructorGraph/changeEdgeData',
        'constructorGraph/changeVertexData'
    ]),
    ...mapGetters([
       'tasks/findGraphDataByTaskId',
        'tasks/findTaskById',
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
        this['constructorGraph/updateEdgeColorMutation'](
            {
              "fromV": ele.data().source,
              "toV": ele.data().target,
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

      if (this.graphInfo.permission.color) {
        cy.cxtmenu({
          menuRadius: function(ele){ return 75; },
          selector: 'node, edge',
          outsideMenuCancel: 10,
          commands: [
            {
              fillColor: 'red',
              content: '',
              select: (ele) => {
                this.changeElemColor(ele, 'red')
              }
            },
            {
              fillColor: 'pink',
              content: '',
              select: (ele) => {
                this.changeElemColor(ele, 'pink')
              }
            },
            {
              fillColor: 'blue',
              content: '',
              select: (ele) => {
                this.changeElemColor(ele, 'blue')
              }
            },
            {
              fillColor: 'green',
              content: '',
              select: (ele) => {
                this.changeElemColor(ele, 'green')
              }
            },
            {
              fillColor: 'yellow',
              content: '',
              select: (ele) => {
                this.changeElemColor(ele, 'yellow')
              }
            },
            {
              fillColor: 'brown',
              content: '',
              select: (ele) => {
                this.changeElemColor(ele, 'brown')
              }
            },
            {
              fillColor: 'gray',
              content: '',
              select: (ele) => {
                this.changeElemColor(ele, 'gray')
              }
            },
          ],
          activeFillColor: 'rgba(103,58,183,0.25)'
        });
      }

      if (this.graphInfo.direct) {
        cy.style().fromJson(directedStyle).update()
      } else {
        cy.style().fromJson(undirectedStyle).update()
      }

      cy.on('ehcomplete', (event, sourceNode, targetNode, addedEdge) => {
        const edgeWeight = addedEdge.data().weight
        addedEdge.data().name = this.edgeName
        addedEdge.data().color = 'gray'
        addedEdge.data().classes = 'top'
        console.log(addedEdge)
        this['constructorGraph/addEdgeMutation']({
          "name":  this.edgeName,
          "color" : 'gray',
          "fromV" : sourceNode.data().name,
          "toV" : targetNode.data().name,
          "weight" : 0,
          "label": ''
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
              this.$store.getters["constructorGraph/findVertexById"](target.data().name).weight
          this.selectedElement.label =
              this.$store.getters["constructorGraph/findVertexById"](target.data().name).label
          this.selectedElement.name = target.data().name
          this.selectedElement.elementType = "vertex"
          this.dialog = true
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
            this.dialog = true
          }
        }
      }

    },
    addNode(event) {
      let {position} = event
      if (event.target === event.cy) {
        const newName = this.$store.getters["constructorGraph/nextVertexName"]
        event.cy.add({
          group: 'nodes',
          data: {
            id: newName,
            name: newName,
            color: 'gray'
          },
          position: {x: position.x, y: position.y}
        })
        this['constructorGraph/addNodeMutation'](
            {
              "index": newName,
              "name": newName,
              "color": 'gray',
              "weight": 0,
              "label": '',
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
          this['constructorGraph/removeEdgeMutation']({
            "name" : target.data().name,
            "fromV" : target.data().source,
            "toV" : target.data().target
          })
        } else {
          let index = target.data().name
          console.log(index)
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
      if (elementType === 'edge') {
        this['constructorGraph/changeEdgeData']({
          "name" : name,
          "label" : this.selectedElement.label,
          "weight" : this.selectedElement.weight
        })
        this.selectedElement.edge.data('weight', this.selectedElement.weight)
      } else {
        if (elementType === 'vertex')  {
          this['constructorGraph/changeVertexData']({
            "name" : name,
            "label" : this.selectedElement.label,
            "weight" : this.selectedElement.weight
          })
        }
      }
      this.dialog = false
    }
  }
}
</script>

<style>

#cy {
  width: 100%;
  height: 100%;
  background-color: #e8ebf6;
}

#cytoscape-div {
  width: 100%;
  height: 95%;
}

custom-context-menu {
  border-color: purple !important;

}

</style>