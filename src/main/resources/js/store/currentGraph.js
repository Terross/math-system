const defaultState = {
    vertexList: [],
    edgeList: [],
    edgeCount: 0,
    vertexCount: 0,
    editType: 'move', //'edit', 'remove', 'draw'
    reg: false
}

const state = () => (defaultState)
const getters = {
    findVertexById: (state) => id => {
        return state.vertexList.find(item => item.id === id)
    },
    findEdgeById: (state) => id => {
        return state.edgeList.find(item => item.id === id)
    },
    cytoscapeConfigElements: (state) => {
        let elements = [];
        state.vertexList.forEach(vertex => {
            const weightData = vertex.weight != null ? vertex.weight : ""
            const labelData = vertex.label != null ? vertex.label : ""
            const label = weightData + '\n' + labelData

            elements.push({
                group: 'nodes',
                data: {
                    label: label,
                    id: vertex.id,
                    color: vertex.color
                },
                classes: 'multiline-manual',
                position: {x: vertex.xCoordinate, y: vertex.yCoordinate}
            })
        })
        state.edgeList.forEach(edge => {
            const weightData = edge.weight != null ? edge.weight : ""
            const labelData = edge.label != null ? edge.label : ""
            const label = weightData + '\n' + labelData
            elements.push({
                group: 'edges',
                data: {
                    label: label,
                    source: edge.fromV,
                    target: edge.toV,
                    id: edge.id,
                    color: edge.color
                },
                classes: 'multiline-manual'
            })
        })
        return elements;
    }
}
const mutations = {
    changeEdgeData(state, edge) {
        const index = state.edgeList.findIndex(item => item.id === edge.id)
        state.edgeList[index].label = edge.label
        state.edgeList[index].weight = edge.weight
    },
    changeVertexData(state, node) {
        const index = state.vertexList.findIndex(item => item.id === node.id)
        state.vertexList[index].label = node.label
        state.vertexList[index].weight = node.weight
    },
    changeVertexCoordinateMutation(state, node) {
        const index = state.vertexList.findIndex(item => item.id === node.id)
        state.vertexList[index].xCoordinate = node.xCoordinate
        state.vertexList[index].yCoordinate = node.yCoordinate
    },
    cleanGraphMutation(state) {
        state.vertexList = []
        state.edgeList = []
        state.edgeCount = 0
        state.vertexCount = 0
        state.editType = 'move'
    },
    initMutation(state, graphData) {
        state.editType = 'move'
        state.vertexList = graphData.vertexList
        state.edgeList = graphData.edgeList
        state.edgeCount = graphData.edgeCount
        state.vertexCount = graphData.vertexCount
    },
    addNodeMutation(state, node) {
        state.vertexList = [
            ...state.vertexList,
            node
        ]
        state.vertexCount = state.vertexCount + 1
    },
    addEdgeMutation(state, edge) {
        state.edgeList = [
            ...state.edgeList,
            edge
        ]
        state.edgeCount = state.edgeCount + 1
    },
    removeNodeMutation(state, node) {
        const index = state.vertexList.findIndex(item => item.id === node.id)
        state.vertexList = [
            ...state.vertexList.slice(0, index),
            ...state.vertexList.slice(index + 1)
        ]
        state.edgeList.forEach(edge => {
            if (edge.fromV === node.id || edge.toV === node.id) {
                const edgeIndex = state.edgeList.findIndex(item => item.id === edge.id)
                state.edgeList = [
                    ...state.edgeList.slice(0, edgeIndex),
                    ...state.edgeList.slice(edgeIndex + 1)
                ]
                state.edgeCount = state.edgeCount - 1
            }
        })
        state.vertexCount = state.vertexCount - 1
    },
    removeEdgeMutation(state, edge) {
        const index = state.edgeList.findIndex(item => item.id === edge.id)
        state.edgeList = [
            ...state.edgeList.slice(0, index),
            ...state.edgeList.slice(index + 1)
        ]
        state.edgeCount = state.edgeCount - 1
    },
    updateEdgeColorMutation(state, edge) {
        const index = state.edgeList.findIndex(item => item.id === edge.id)
        state.edgeList[index].color = edge.color
    },
    updateEdgeWeightMutation(state, edge) {
        const index = state.edgeList.findIndex(item => item.id === edge.id)
        state.edgeList[index].weight = edge.weight
    },
    updateNodeColorMutation(state, node) {
        const index = state.vertexList.findIndex(item => item.id === node.id)
        state.vertexList[index].color = node.color
    },
    changeEditTypeMutation(state, newType) {
        state.editType = newType
    },
    registrationMutation(state) {
        state.reg = true
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations
}