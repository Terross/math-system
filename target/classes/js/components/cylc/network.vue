<template>
  <cytoscape ref="cy"
             id="cy"
             :config="config"
             :afterCreated="afterCreated"
             :preConfig="preConfig"
             v-on:tap="leftClick"
             v-on:cxttapstart="rightClick"
  >
    <v-btn
        class="ma-2"
        fab
        dark
        small
        color="indigo lighten-1"
        id="zoom"
    >
      <v-icon dark>
        zoom_out_map
      </v-icon>
    </v-btn>

  </cytoscape>
</template>

<script>
import {mapActions, mapGetters, mapMutations} from 'vuex'
import edgehandles from 'cytoscape-edgehandles'
import contextMenus from 'cytoscape-context-menus'
import 'cytoscape-context-menus/cytoscape-context-menus.css';
import cola from 'cytoscape-cola'
import dagre from 'cytoscape-dagre';


export default {
  name: "network",
  data() {
    return {
      selectedData: {
        data: null,
        color: '',
        group: ''
      },
      reg: this.$store.state.constructorGraph.reg,
      directedStyle: [
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
            'target-arrow-shape': 'triangle',
          }
        },
        {
          selector: 'edge[color]',
          style: {
            'curve-style': 'bezier',
            'target-arrow-shape': 'triangle',
            'line-color' : 'data(color)',
            'background-color' : 'data(color)',
            'target-arrow-color': 'data(color)'
          }
        },
        {
          selector: 'edge[name]',
          style: {
            'curve-style': 'bezier',
            'target-arrow-shape': 'triangle',
            'content': 'data(name)',
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
          selector: 'edge[name]',
          style: {
            'curve-style': 'bezier',
            'content': 'data(name)',
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
    permission() {
      if (this.networkType === 'constructor') {
        return {
          "edit" : true,
          "color" : true,
          "weight" : true,
          "remove" : true
        }
      } else {
        return this.$store.state.constructorGraph.permission
      }
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
        'constructorGraph/cleanGraphMutation'
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
        this['constructorGraph/registrationMutation']()
      }
    },
    afterCreated(cy) {
      let eh = cy.edgehandles()
      const directedStyle = this.directedStyle
      const undirectedStyle = this.undirectedStyle

      if (this.graphInfo.editType === 'edit') {
        eh.enableDrawMode()
      }

      if (this.graphInfo.direct) {
        cy.style().fromJson(directedStyle).update()
      } else {
        cy.style().fromJson(undirectedStyle).update()
      }

      cy.on('ehcomplete', (event, sourceNode, targetNode, addedEdge) => {
        const edgeWeight = addedEdge.data().weight
        addedEdge.data().color = 'gray'
        this['constructorGraph/addEdgeMutation']({
          "color" : this.elementData.enableColor ? this.elementData.color : 'gray',
          "fromV" : sourceNode.data().name,
          "toV" : targetNode.data().name,
          "weight" : edgeWeight == null ? '' : edgeWeight
        })
      });

      document.getElementById('testId2').addEventListener('click', function () {
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
      })
      document.querySelector('#graphModeEdit').addEventListener('click', function() {
        eh.enableDrawMode()
      })
      document.querySelector('#graphModeRemove').addEventListener('click', function() {
        eh.disableDrawMode()
      })
      document.querySelector('#graphModeMove').addEventListener('click', function() {
        eh.disableDrawMode()
      })
      if (document.querySelector('#directed-type-graph') != null) {
        document.querySelector('#directed-type-graph').addEventListener('click', function() {
          cy.style().fromJson(directedStyle).update()
        })
        document.querySelector('#undirected-type-graph').addEventListener('click', function() {
          cy.style().fromJson(undirectedStyle).update()
        })
      }

      document.querySelector('#zoom').addEventListener('click', function () {
        cy.fit()
      })

    },
    leftClick(event) {
      switch (this.editType) {
        case 'edit':
          this.addNode(event)
          break
        case 'move':
          break
        case 'remove':
          this.removeElem(event)
          break
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
              "name": this.$store.getters["constructorGraph/nextVertexName"],
              "color": 'gray',
              "outgoingEdges" : [],
              "incomingEdges" :[]
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
            "fromV" : target.data().source,
            "toV" : target.data().target
          })
        } else {
          this['constructorGraph/removeNodeMutation']({
            "name" : target.data().name
          })
        }
      }
    },
    rightClick(event) {
      let {target} = event
      let {cy} = event
      if (target !== cy) {
        const color = this.elementData.color.toString()
        const weight = this.elementData.weight
        const colorEnable = this.elementData.enableColor
        const weightEnable = this.elementData.enableWeight
        if (color.length > 0 && colorEnable) {
          if (target.group().toString() === 'edges') {
            target.style('background-color', color)
            target.style('line-color', color)
            target.style('target-arrow-color', color)
            this['constructorGraph/updateEdgeColorMutation'](
                {
                  "fromV": target.data().source,
                  "toV": target.data().target,
                  "color": color
                }
            )
          } else {
            target.style('background-color', color)
            this['constructorGraph/updateNodeColorMutation'](
                {
                  "name" : target.data().name,
                  "color": color
                }
            )
          }
        }
        if (weight.length > 0 && isFinite(weight) && weightEnable) {
          if (target.group().toString() === 'edges') {
            target.style('label', this.elementData.weight)
            this['constructorGraph/updateEdgeWeightMutation'](
                {
                  "fromV": target.data().source,
                  "toV": target.data().target,
                  "weight": weight
                }
            )
          }
        }
      }
    }
  }
}
</script>

<style>

#cy {
  width: 100%;
  height: 100%;
  background-color: #e8eaf6 ;
}

#cytoscape-div {
  width: 100%;
  height: 95%;
}

custom-context-menu {
  border-color: purple !important;

}

</style>