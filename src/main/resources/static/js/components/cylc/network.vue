<template>
  <v-container style="height: 100%">
    <div id="cy" class="cy"></div>
  </v-container>
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
      mode : 'move'
    }
  },
  computed: mapGetters(['graphData']),
  mounted: function () {
    console.log(this.graphData)
    document.getElementById("elementLabel")
        .addEventListener('change', (event) => {
      this.labelData = event.target.value;
    })
    cy = cytoscape({
      container: document.getElementById('cy'),
      layout: {
        name: 'breadthfirst'
      },
      elements: this.graphData
    })
    console.log(cy.nodes().length)
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
  },
  methods: {
    ...mapActions(['addVertexAction']),
    setCytocape(cy, eh) {
      switch(this.mode) {
        case "move":
          eh.disableDrawMode()
          cy.contextMenus({})
          cy.removeListener('tap');
          break
        case "edit":
          this.setMutableContextMenu(cy, this)
          this.setMutable(cy, eh, this)
          break
        case "color":
          eh.disableDrawMode()
          this.setColorContextMenu(cy)
          cy.removeListener('tap');
          break
      }
    },
    setMutable(cy ,eh, v) {
      eh.enableDrawMode()
      cy.on('tap', function (event) {
        let {position} = event
        if (event.target === cy) {
          const vertex = {
            adjacencyList: null,
            color: "153,153,153",
            id: cy.nodes().length
          }
          v.addVertexAction(vertex)
          cy.add({
            group: 'nodes',
            data: {id: cy.nodes().length, name: cy.nodes().length},
            position: {x: position.x, y: position.y}
          })
        }
      })
    },
    setMutableContextMenu(cy, v) {
      let removed = false
      var contextMenu = cy.contextMenus({
        menuItems: [
          {
            id: 'remove',
            content: 'remove',
            tooltipText: 'remove',
            image: {src: "https://img.icons8.com/material/24/000000/delete-sign--v1.png", width: 12, height: 12, x: 6, y: 4},
            selector: 'node, edge',
            onClickFunction: function (event) {
              var target = event.target || event.cyTarget;
              removed = target.remove();
              contextMenu.showMenuItem('undo-last-remove');
            },
            hasTrailingDivider: true
          },
          {
            id: 'undo-last-remove',
            content: 'undo last remove',
            selector: 'node, edge',
            show: false,
            coreAsWell: true,
            onClickFunction: function (event) {
              if (removed) {
                removed.restore();
              }
              contextMenu.hideMenuItem('undo-last-remove');
            },
            hasTrailingDivider: true
          },
          {
            id: 'color',
            content: 'change color',
            tooltipText: 'change color',
            selector: 'node, edge',
            hasTrailingDivider: true,
            submenu: [
              {
                id: 'color-blue',
                content: 'blue',
                tooltipText: 'blue',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.backgrounding() === false) {
                    target.style('line-color', 'blue')
                    target.style('target-arrow-color', 'blue')
                  }
                  target.style('background-color', 'blue');
                }
              },
              {
                id: 'color-green',
                content: 'green',
                tooltipText: 'green',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.backgrounding() === false) {
                    target.style('line-color', 'green')
                    target.style('target-arrow-color', 'green')
                  }
                  target.style('background-color', 'green');
                },
              },
              {
                id: 'color-red',
                content: 'red',
                tooltipText: 'red',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.backgrounding() === false) {
                    target.style('line-color', 'red')
                    target.style('target-arrow-color', 'red')
                  }
                  target.style('background-color', 'red');
                },
              },
              {
                id: 'color-yellow',
                content: 'yellow',
                tooltipText: 'yellow',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.backgrounding() === false) {
                    target.style('line-color', 'yellow')
                    target.style('target-arrow-color', 'yellow')
                  }
                  target.style('background-color', 'yellow');
                }
              },
              {
                id: 'color-maroon',
                content: 'maroon',
                tooltipText: 'maroon',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.backgrounding() === false) {
                    target.style('line-color', 'maroon')
                    target.style('target-arrow-color', 'maroon')
                  }
                  target.style('background-color', 'maroon');
                },
              },
              {
                id: 'color-purple',
                content: 'purple',
                tooltipText: 'purple',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  if (target.backgrounding() === false) {
                    target.style('line-color', 'purple')
                    target.style('target-arrow-color', 'purple')
                  }
                  target.style('background-color', 'purple');
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

              target.data('name', v.labelData)
            }
          }
        ],
        menuItemClasses: ['custom-menu-item'],
        contextMenuClasses: ['custom-context-menu'],
        submenuIndicator: { src: 'https://img.icons8.com/material-outlined/24/000000/menu-2.png', width: 12, height: 12 }
      });
    },
    setColorContextMenu(cy) {
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
                  target.style('background-color', 'blue');
                }
              },
              {
                id: 'color-green',
                content: 'green',
                tooltipText: 'green',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  target.style('background-color', 'green');
                },
              },
              {
                id: 'color-red',
                content: 'red',
                tooltipText: 'red',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  target.style('background-color', 'red');
                },
              },
              {
                id: 'color-yellow',
                content: 'yellow',
                tooltipText: 'yellow',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  target.style('background-color', 'yellow');
                }
              },
              {
                id: 'color-maroon',
                content: 'maroon',
                tooltipText: 'maroon',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  target.style('background-color', 'maroon');
                },
              },
              {
                id: 'color-purple',
                content: 'purple',
                tooltipText: 'purple',
                onClickFunction: function (event) {
                  let target = event.target || event.cyTarget;
                  target.style('background-color', 'purple');
                },
              }
            ]
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
  position: relative;
  left: 0;
  top: 0;
  bottom: 0;
  right: 0;
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