
const state = () => ({
    constructorGraph: [],
    editType: 'move', //'edit', 'remove'
    changeLabel: true,
    direct: true,
    elementData: {
        color: '',
        weight: ''
    },
    reg: false,
    graphPresent: false
})

const mutations = {
    addNodeMutation(state, node) {
        state.constructorGraph = [
            ...state.constructorGraph,
            node
        ]
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
    },
    removeNodeMutation(state, node) {
        const removeIndex = state.constructorGraph.findIndex(
            item => item.name === node.name
        )

        state.constructorGraph = [
            ...state.constructorGraph.slice(0, removeIndex),
            ...state.constructorGraph.slice(removeIndex + 1)
        ]
    },
    removeEdgeMutation(state, edge) {

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

        state.constructorGraph[vertexSourceIndex].outgoingEdges = [
            ...outArray.slice(0, outArrayIndex),
            ...outArray.slice(outArrayIndex + 1)
        ]

        state.constructorGraph[vertexTargetIndex].incomingEdges = [
            ...inArray.slice(0, inArrayIndex),
            ...inArray.slice(inArrayIndex + 1)
        ]
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
    updateNodeColorMutation(state, node, color) {
        const index = state.constructorGraph.findIndex(
            item => item.name.toString() === node.toString()
        )

        state.constructorGraph[index].color = color
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
    }
}

const actions = {

}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}