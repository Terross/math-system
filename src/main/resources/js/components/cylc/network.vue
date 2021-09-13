<template>
    <v-card id="cy" class="cy"></v-card>
</template>

<script>
import {mapActions} from 'vuex'
import cytoscape from 'cytoscape'
import edgehandles from 'cytoscape-edgehandles'
import contextMenus from 'cytoscape-context-menus'
import 'cytoscape-context-menus/cytoscape-context-menus.css';
import { mapGetters } from 'vuex'

cytoscape.use(edgehandles)
cytoscape.use(contextMenus)

export default {
  name: "network",
  data() {
    return {
      labelData: '',
      mode : 'move',
      id : 0,
      graphData: this.$route.path.includes('/task/')
          ? this.$store.getters.findGraphDataByTaskId(this.$store.getters.findTaskById(this.$route.params.id))
          : null
    }
  },

  props: {
    graphExist : Boolean
  },

  mounted: function () {
    document.getElementById("elementLabel")
        .addEventListener('change', (event) => {
      this.labelData = event.target.value;
    })

    var cy = cytoscape({
      container: document.getElementById('cy'),
      layout: {
        name: 'circle'
      },
      elements: this.graphData
    })

    var eh = cy.edgehandles();
    this.setGraphStyle(cy)
    this.setCytocape(cy, eh)
    document.getElementById("graphModeMove")
        .addEventListener('click', () => {
          this.mode = "move"
          this.setCytocape(cy, eh)
        })
    document.getElementById("graphModeEdit")
        .addEventListener('click', () => {
          this.mode = "edit"
          this.setCytocape(cy, eh)
        })
    document.getElementById("graphModeColor")
        .addEventListener('click', () => {
          this.mode = "color"
          this.setCytocape(cy, eh)
        })
    document.getElementById("graphModeRemove")
        .addEventListener('click', () => {
          this.mode = "remove"
          this.setCytocape(cy, eh)
        })
  },

  methods: {
    setCytocape(cy, eh) {
      switch(this.mode) {
        case "move":
          eh.disableDrawMode()
          cy.removeListener('ehcomplete');
          cy.removeListener('tap');
          this.setColorContextMenu(cy, this)
          break
        case "edit":
          cy.removeListener('ehcomplete');
          cy.removeListener('tap');
          this.setColorContextMenu(cy, this)
          this.setMutable(cy, eh, this)
          break
        case "color":
          cy.removeListener('ehcomplete');
          cy.removeListener('tap');
          eh.disableDrawMode()
          this.setColorContextMenu(cy, this)
          break
        case "remove":
          cy.removeListener('ehcomplete');
          cy.removeListener('tap');
          eh.disableDrawMode()
          this.setColorContextMenu(cy, this)
          this.setRemovable(cy, this)
          break
      }
    },
    setRemovable(cy, v) {
      cy.on('tap', function (event) {
        let {target} = event
        if (target !== cy) {
          cy.remove(target)

          if (target.group().toString() === 'edges') {
            v.$parent.$parent.edgeCount -= 1

            console.log(v.$parent.$parent.graphVertexies)
            console.log(target.data())
            const outList = v.$parent.$parent.graphVertexies
                .filter(item => item.name.toString() === target.data().source)[0]
                .outgoingEdges;
            const removeFromOutIndex = outList.findIndex(item => item.fromV.toString() === target.data().source)

            v.$parent.$parent.graphVertexies
                .filter(item => item.name.toString() === target.data().source)[0]
                .outgoingEdges = [
                    ...outList.slice(0, removeFromOutIndex),
                    ...outList.slice(removeFromOutIndex + 1)
                ]

            const inList = v.$parent.$parent.graphVertexies
                .filter(item => item.name.toString() === target.data().target)[0]
                .incomingEdges;
            const removeFromInIndex = inList.findIndex(item => item.toV.toString() === target.data().target)
            v.$parent.$parent.graphVertexies
                .filter(item => item.name.toString() === target.data().target)[0]
                .incomingEdges = [
              ...inList.slice(0, removeFromInIndex),
              ...inList.slice(removeFromInIndex + 1)
            ]

          } else {
            if (target.group().toString() === 'nodes') {
              const removeIndex = v.$parent.$parent.graphVertexies.findIndex(item => item.name === target.data().name)
              v.$parent.$parent.graphVertexies = [
                ...v.$parent.$parent.graphVertexies.slice(0, removeIndex),
                ...v.$parent.$parent.graphVertexies.slice(removeIndex + 1)
              ]

            }
          }
        }
      })
    },
    setMutable(cy ,eh, v) {
      eh.enableDrawMode()
      cy.on('ehcomplete', (event, sourceNode, targetNode, addedEdge) => {
        const edgeWeight = addedEdge.data().weight
        v.$parent.$parent.graphVertexies.find(e => e.name === sourceNode.data().name).outgoingEdges.push({
          "fromV" : sourceNode.data().name,
          "toV" : targetNode.data().name,
          "weight" : edgeWeight == null ? 0 : edgeWeight
        })
        v.$parent.$parent.graphVertexies.find(e => e.name === targetNode.data().name).incomingEdges.push({
          "fromV" : sourceNode.data().name,
          "toV" : targetNode.data().name,
          "weight" : edgeWeight == null ? 0 : edgeWeight
        })
        v.$parent.$parent.edgeCount += 1
      });

      cy.on('tap', function (event) {
        let {position} = event
        if (event.target === cy) {

          cy.add({
            group: 'nodes',
            data: {
              id: v.id,
              name: v.id},
            position: {x: position.x, y: position.y}
          })

          v.$parent.$parent.graphVertexies.push({
            "name" : v.id,
            "color": "gray",
            "outgoingEdges" : [],
            "incomingEdges" :[]
          })
          v.id += 1
        }
      })
    },

    setColorContextMenu(cy, v) {
      cy.contextMenus({
        menuItems: [
          {
            id: 'color',
            content: 'change color',
            tooltipText: 'change color',
            selector: 'node',
            hasTrailingDivider: true,
            submenu: [
              {
                id: 'color-blue',
                content: 'blue',
                tooltipText: 'blue',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.group() === 'nodes') {
                    target.style('background-color', 'blue');
                    v.$parent.$parent.graphVertexies.filter(item => item.name === target.data().name)[0].color = 'blue'
                  }
                }
              },
              {
                id: 'color-green',
                content: 'green',
                tooltipText: 'green',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.group() === 'nodes') {
                    target.style('background-color', 'green');
                    v.$parent.$parent.graphVertexies.filter(item => item.name === target.data().name)[0].color = 'green'
                  }
                },
              },
              {
                id: 'color-red',
                content: 'red',
                tooltipText: 'red',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.group() === 'nodes') {
                    target.style('background-color', 'red');
                    v.$parent.$parent.graphVertexies.filter(item => item.name === target.data().name)[0].color = 'red'
                  }
                },
              },
              {
                id: 'color-yellow',
                content: 'yellow',
                tooltipText: 'yellow',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.group() === 'nodes') {
                    target.style('background-color', 'yellow');
                    v.$parent.$parent.graphVertexies.filter(item => item.name === target.data().name)[0].color = 'yellow'
                  }
                }
              },
              {
                id: 'color-maroon',
                content: 'maroon',
                tooltipText: 'maroon',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.group() === 'nodes') {
                    target.style('background-color', 'maroon');
                    v.$parent.$parent.graphVertexies.filter(item => item.name === target.data().name)[0].color = 'maroon'
                  }

                },
              },
              {
                id: 'color-purple',
                content: 'purple',
                tooltipText: 'purple',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.group() === 'nodes') {
                    target.style('background-color', 'purple');
                    v.$parent.$parent.graphVertexies.filter(item => item.name === target.data().name)[0].color = 'purple'
                  }

                },
              }
            ]
          },
          {
            id: 'changeContent',
            content: 'change label',
            tooltipText: 'change label',
            selector: 'node, edge',
            onClickFunction: function(event) {
              var target = event.target || event.cyTarget;
              if (v.labelData.length > 0) {

                  if (target.group() === 'edges') {
                    v.$parent.$parent.graphVertexies
                        .filter(item => item.name.toString() === target.data().source)[0]
                        .outgoingEdges.filter(item => item.toV.toString() === target.data().target)[0]
                        .weight = v.labelData
                    v.$parent.$parent.graphVertexies
                        .filter(item => item.name.toString() === target.data().target)[0]
                        .incomingEdges.filter(item => item.fromV.toString() === target.data().source)[0]
                        .weight = v.labelData

                    target.data('name', v.labelData)
                  }
                }
            }
          }
        ], menuItemClasses: ['custom-menu-item'],
          contextMenuClasses: ['custom-context-menu'],
          submenuIndicator: { src: 'https://img.icons8.com/material-outlined/24/000000/menu-2.png', width: 12, height: 12 }
    })
    },
    setGraphStyle(cy) {
      cy.style([
        {
          selector: 'node[name]',
          style: {
            'content': 'data(name)',
            'background-color': "rgb(153,153,153)"
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
        }
      ])
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

custom-menu-item {
  border-color: white !important;
  border-radius: 5px;
  color:white !important;
  background-color: purple;
  font-weight: bold !important;

}

custom-context-menu {
  border-color: purple !important;

}

</style>