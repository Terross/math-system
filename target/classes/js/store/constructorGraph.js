import {deleteEdge} from '../util/collections.js'


const defaultState = {
    constructorGraph: [],
    edgeCount: 0,
    vertexCount: 0,
    editType: 'move', //'edit', 'remove'
    changeLabel: true,
    direct: true,
    elementData: {
        color: 'gray',
        weight: '0',
        enableColor: true,
        enableWeight: false
    },
    reg: false,
    graphPresent: false,
    permission: {
        "edit" : true,
        "color" : true,
        "weight" : true,
        "remove" : true
    }
}

const state = () => (defaultState)
const getters = {
    nextVertexName: (state) => {
      let name = 0
      while (true) {
          if (state.constructorGraph.filter(item =>
              item.name.toString() === name.toString()).length > 0) {
              name++
          } else {
              break;
          }
      }
      return name
    },
    cytoscapeConfigElements: (state) => {
        let elements = [];
        let adj = state.constructorGraph;
        for (let i = 0; i < adj.length; i++) {
            elements.push(
                {
                    group: 'nodes',
                    data: {
                        label: '',
                        id: i,
                        name: adj[i].name,
                        color: adj[i].color
                    }
                }
            )
        }
        let idForEdge = adj.length
        for (let i = 0; i < adj.length; i++) {
            let edge = adj[i].incomingEdges;
            for (let j = 0; j < edge.length; j++) {
                let edgeSource = elements.find(v => v.data.name === edge[j].fromV && v.group === 'nodes')
                let edgeTarget = elements.find(v => v.data.name === edge[j].toV  && v.group === 'nodes')
                if (edgeSource !== null && edgeTarget !== null) {

                    elements.push(
                        {
                            group: 'edges',
                            data: {
                                label: '',
                                source: edgeSource.data.id,
                                target: edgeTarget.data.id,
                                name: edge[j].weight,
                                color: edge[j].color
                            }
                        }
                    )
                    idForEdge++;
                }

            }
        }
        return elements;
    }
}
const mutations = {
    updatePermissionMutation(state, newPermission) {
        state.permission = newPermission
    },
    cleanGraphMutation(state) {
        state.constructorGraph = []
        state.edgeCount = 0
        state.vertexCount = 0
        state.editType = 'move'
        state.changeLabel = true
        state.direct = true
        state.permission = {
            "edit" : true,
            "color" : true,
            "weight" : true,
            "remove" : true
        }
    },
    initMutation(state, graphData) {
        state.editType = 'move'
        state.constructorGraph = graphData.vertexes
        state.edgeCount = graphData.edgeCount
        state.vertexCount = graphData.vertexCount
        state.permission = graphData.permission
        state.direct = graphData.direct
        state.elementData =  {
                color: 'gray',
                weight: '0',
                enableColor: false,
                enableWeight: false
        }
    },
    addNodeMutation(state, node) {
        state.constructorGraph = [
            ...state.constructorGraph,
            node
        ]
        state.vertexCount = state.vertexCount + 1
    },
    addEdgeMutation(state, edge) {
        const vertexSourceIndex = state.constructorGraph.findIndex(
            item => item.name === edge.fromV
        )
        const vertexTargetIndex = state.constructorGraph.findIndex(
            item => item.name === edge.toV
        )

        state.constructorGraph[vertexSourceIndex].outgoingEdges.push(edge)
        state.constructorGraph[vertexTargetIndex].incomingEdges.push(edge)
        state.edgeCount = state.edgeCount + 1
    },
    removeNodeMutation(state, node) {
        const removeIndex = state.constructorGraph.findIndex(
            item => item.name === node.name
        )

        const removeNode = state.constructorGraph[removeIndex]
        const outEdges = removeNode.outgoingEdges
        const inEdges = removeNode.incomingEdges
        for (let i = 0; i < outEdges.length; i++) {
            deleteEdge(state,  outEdges[i])
        }
        for (let i = 0; i < inEdges.length; i++) {
            deleteEdge(state,  inEdges[i])
        }

        state.constructorGraph = [
            ...state.constructorGraph.slice(0, removeIndex),
            ...state.constructorGraph.slice(removeIndex + 1)
        ]

        state.vertexCount = state.vertexCount - 1
    },
    removeEdgeMutation(state, edge) {
        deleteEdge(state, edge)
    },
    updateEdgeColorMutation(state, edge) {
        const color = edge.color
        const vertexSourceIndex = state.constructorGraph.findIndex(
            item => item.name.toString() === edge.fromV.toString()
        )
        const vertexTargetIndex = state.constructorGraph.findIndex(
            item => item.name.toString() === edge.toV.toString()
        )

        const outArray = state.constructorGraph[vertexSourceIndex].outgoingEdges
        const inArray = state.constructorGraph[vertexTargetIndex].incomingEdges

        const outArrayIndex = outArray.findIndex(
            item => item.toV.toString() === edge.toV.toString()
        )
        const inArrayIndex = inArray.findIndex(
            item => item.fromV.toString() === edge.fromV.toString()
        )
        console.log(color)
        state.constructorGraph[vertexSourceIndex].outgoingEdges[outArrayIndex].color = color
        state.constructorGraph[vertexTargetIndex].incomingEdges[inArrayIndex].color = color
    },
    updateEdgeWeightMutation(state, edge) {
        const weight = edge.weight
        const vertexSourceIndex = state.constructorGraph.findIndex(
            item => item.name.toString() === edge.fromV.toString()
        )
        const vertexTargetIndex = state.constructorGraph.findIndex(
            item => item.name.toString() === edge.toV.toString()
        )

        const outArray = state.constructorGraph[vertexSourceIndex].outgoingEdges
        const inArray = state.constructorGraph[vertexTargetIndex].incomingEdges

        const outArrayIndex = outArray.findIndex(
            item => item.toV.toString() === edge.toV.toString()
        )
        const inArrayIndex = inArray.findIndex(
            item => item.fromV.toString() === edge.fromV.toString()
        )

        state.constructorGraph[vertexSourceIndex].outgoingEdges[outArrayIndex].weight = weight
        state.constructorGraph[vertexTargetIndex].incomingEdges[inArrayIndex].weight = weight
    },
    updateNodeColorMutation(state, node) {
        console.log(node.color)
        const index = state.constructorGraph.findIndex(
            item => item.name.toString() === node.name.toString()
        )
        state.constructorGraph[index].color = node.color
    },
    changeEditTypeMutation(state, newType) {
        state.editType = newType
    },
    changeDirectTypeMutation(state, newType) {
        state.direct = newType
    },
    selectedDataMutation(state, newData) {
        state.selectedData = newData
    },
    registrationMutation(state) {
        state.reg = true
    },
    changeElementDataMutation(state, newData) {
        state.elementData = newData
    },
    graphPresentMutation(state) {
        state.graphPresent = !state.graphPresent
    },

}

const actions = {

}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}