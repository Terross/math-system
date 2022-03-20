import {deleteEdge} from '../util/collections.js'


const defaultState = {
    constructorGraph: [],
    edgeCount: 0,
    vertexCount: 0,
    editType: 'move', //'edit', 'remove', 'draw'
    reg: false
}

const state = () => (defaultState)
const getters = {
    findVertexById: (state) => id => {
        return state.constructorGraph.find(item =>
        item.name === id)
    },
    findEdgeById: (state) => id => {
        for (let i = 0; i < state.vertexCount; i++) {
            let edge = state.constructorGraph[i].outgoingEdges.find(
                item => item.name === id
            )
            if (typeof edge != "undefined") {
                return edge
            }
        }
    },
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
    nextVertexIndex: (state) => {
        let index = 0
        while (true) {
            if (state.constructorGraph.filter(item =>
                item.index.toString() === index.toString()).length > 0) {
                index++
            } else {
                break
            }
        }
        console.log(index)
        return index
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
                        id: adj[i].name,
                        name: adj[i].name,
                        color: adj[i].color
                    }
                }
            )
        }
        for (let i = 0; i < adj.length; i++) {
            let edge = adj[i].incomingEdges;

            for (let j = 0; j < edge.length; j++) {
                let edgeSource = elements.find(v => v.data.name.toString() === edge[j].fromV.toString() && v.group === 'nodes')
                let edgeTarget = elements.find(v => v.data.name.toString() === edge[j].toV.toString()  && v.group === 'nodes')

                if (edgeSource !== null && edgeTarget !== null) {

                    elements.push(
                        {
                            group: 'edges',
                            data: {
                                label: '',
                                source: edgeSource.data.id,
                                target: edgeTarget.data.id,
                                weight: edge[j].weight,
                                name: edge[j].name,
                                color: edge[j].color
                            }
                        }
                    )
                }
            }
        }
        return elements;
    }
}
const mutations = {
    changeEdgeData(state, edge) {
        for (let i = 0; i < state.vertexCount; i++) {
            let index = state.constructorGraph[i].outgoingEdges.findIndex(
                item => item.name === edge.name
            )
            if (index > -1) {
                state.constructorGraph[i].outgoingEdges[index].label = edge.label
                state.constructorGraph[i].outgoingEdges[index].weight = edge.weight
            }
        }
    },
    changeVertexData(state, node) {
        const index = state.constructorGraph.findIndex(
            item => item.name.toString() === node.name.toString()
        )
        state.constructorGraph[index].label = node.label
        state.constructorGraph[index].weight = node.weight
    },
    changeVertexCoordinateMutation(state, node) {
        const index = state.constructorGraph.findIndex(
            item => item.name.toString() === node.name.toString()
        )
        state.constructorGraph[index].xCoordinate = node.xCoordinate
        state.constructorGraph[index].yCoordinate = node.yCoordinate
    },
    cleanGraphMutation(state) {
        state.constructorGraph = []
        state.edgeCount = 0
        state.vertexCount = 0
        state.editType = 'move'
    },
    initMutation(state, graphData) {

        state.editType = 'move'
        state.constructorGraph = graphData.vertexes
        state.edgeCount = graphData.edgeCount
        state.vertexCount = graphData.vertexCount
        for (let i = 0; i < state.constructorGraph.length; i++) {
            state.constructorGraph[i].index = state.constructorGraph[i].name
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
        console.log(edge)
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
            item => item.name.toString() === node.name.toString()
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
        let index = node
        for (let i = 0; i < state.vertexCount; i++) {
            let node = state.constructorGraph[i]
            if (node.name > index.name) {
                node.name = (Number(node.name) - 1)


                for (let j = 0; j < node.outgoingEdges.length; j++) {
                    node.outgoingEdges[j].fromV = node.name
                    node.outgoingEdges[j].toV = (Number(node.outgoingEdges[j].toV) - 1).toString()
                }
                for (let j = 0; j < node.incomingEdges.length; j++) {
                    node.incomingEdges[j].toV = node.name
                    node.incomingEdges[j].fromV = (Number(node.incomingEdges[j].fromV) - 1).toString()
                }
            }
        }
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
        const index = state.constructorGraph.findIndex(
            item => item.name.toString() === node.name.toString()
        )
        state.constructorGraph[index].color = node.color
    },
    changeEditTypeMutation(state, newType) {
        state.editType = newType
    },
    registrationMutation(state) {
        state.reg = true
    },

}

export default {
    namespaced: true,
    state,
    getters,
    mutations
}