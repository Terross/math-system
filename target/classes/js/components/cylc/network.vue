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
    <cy-element
        id="cy"
        v-for="def in config.elements"
        :key="`${def.data.id}`"
    />
  </cytoscape>
</template>

<script>
import {mapActions, mapMutations} from 'vuex'
import edgehandles from 'cytoscape-edgehandles'
import contextMenus from 'cytoscape-context-menus'
import 'cytoscape-context-menus/cytoscape-context-menus.css';



export default {
  name: "network",
  data() {
    return {
      graphData: this.$route.path.includes('/task/')
          ? this.$store.getters.findGraphDataByTaskId(this.$store.getters.findTaskById(this.$route.params.id))
          : null,
      nodeId: 0,
      edgeId: 0,
      dialog: false,
      selectedData: {
        data: null,
        color: '',
        group: ''
      },
      cy: null,
      reg: this.$store.state.constructorGraph.reg
    }
  },
  props: {
    networkType: String
  },
  computed: {
    changeColor() {
      return this.networkType === 'task' ? null
          : this.$store.state.constructorGraph.changeColor
    },
    editType() {
      return this.networkType === 'task' ? null
          : this.$store.state.constructorGraph.editType
    },
    changeLabel() {
      return this.networkType === 'task' ? null
          : this.$store.state.constructorGraph.changeLabel
    },
    direct() {
      return this.networkType === 'task' ? null
          : this.$store.state.constructorGraph.direct
    },
    config() {
      return {
        style: this.cytoscapeStyle,
        layout: {
          name: 'circle'
        }
      }
    },
    elementData() {
      return this.$store.state.constructorGraph.elementData
    },
    testGraph() {
      return this.$store.state.constructorGraph.constructorGraph
    },
    cytoscapeStyle() {
      return [{
        selector: 'node[name]',
        style: {
          'content': 'data(name)',
          'background-color': "gray"
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
        }]
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
        'constructorGraph/updateNodeColorMutation'
    ]),
    preConfig(cytoscape) {
      if (!this.reg) {
        cytoscape.use(edgehandles)
        this['constructorGraph/registrationMutation']()
      }
    },
    afterCreated(cy) {
      let eh = cy.edgehandles()
      cy.on('ehcomplete', (event, sourceNode, targetNode, addedEdge) => {
        this.edgeId++
        const edgeWeight = addedEdge.data().weight
        this['constructorGraph/addEdgeMutation']({
          "color" : 'gray',
          "fromV" : sourceNode.data().name,
          "toV" : targetNode.data().name,
          "weight" : edgeWeight == null ? 0 : edgeWeight
        })
      });
      document.querySelector('#graphModeEdit').addEventListener('click', function() {
        eh.enableDrawMode()
      })
      document.querySelector('#graphModeRemove').addEventListener('click', function() {
        eh.disableDrawMode()
      })
      document.querySelector('#graphModeMove').addEventListener('click', function() {
        eh.disableDrawMode()
      })
      document.querySelector('#directed-type-graph').addEventListener('click', function() {
        cy.style().fromJson(
            [
                {
              selector: 'node[name]',
              style: {
                'content': 'data(name)',
                'background-color': "gray"
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
              }]
        ).update()
      })
      document.querySelector('#undirected-type-graph').addEventListener('click', function() {
        cy.style().fromJson(
            [
              {
                selector: 'node[name]',
                style: {
                  'content': 'data(name)',
                  'background-color': "gray"
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
        ).update()
      })
      document.querySelector('#zoom').addEventListener('click', function () {
        console.log('zoom')
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
        event.cy.add({
          group: 'nodes',
          data: {
            id: this.nodeId,
            name: this.nodeId},
          position: {x: position.x, y: position.y}
        })
        this.nodeId++
        this['constructorGraph/addNodeMutation'](
            {
              "name": this.nodeId - 1,
              "color": "gray",
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
        const color = this.elementData.color
        const weight = this.elementData.weight
        if (color.length > 0 && color !== '-') {
          if (target.group().toString() === 'edges') {
            console.log(target.style())
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
                target.data().name,
                color
            )
          }
        }
        if (weight.length > 0 && isFinite(weight)) {
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
  height: 100%;
}


custom-context-menu {
  border-color: purple !important;

}

</style>