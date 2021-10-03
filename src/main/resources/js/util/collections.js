export function deleteEdge(state, edge) {
    console.log(edge)
    const vertexSourceIndex = state.constructorGraph.findIndex(
        item => item.name.toString() === edge.fromV.toString()
    )
    const vertexTargetIndex = state.constructorGraph.findIndex(
        item => item.name.toString() === edge.toV.toString()
    )
    console.log(vertexTargetIndex)

    const outArray = state.constructorGraph[vertexSourceIndex].outgoingEdges
    const inArray = state.constructorGraph[vertexTargetIndex].incomingEdges

    const outArrayIndex = outArray.findIndex(
        item => item.name.toString() === edge.name.toString()
    )
    const inArrayIndex = inArray.findIndex(
        item => item.name.toString() === edge.name.toString()
    )

    state.constructorGraph[vertexSourceIndex].outgoingEdges = [
        ...outArray.slice(0, outArrayIndex),
        ...outArray.slice(outArrayIndex + 1)
    ]

    state.constructorGraph[vertexTargetIndex].incomingEdges = [
        ...inArray.slice(0, inArrayIndex),
        ...inArray.slice(inArrayIndex + 1)
    ]

    state.edgeCount = state.edgeCount - 1
}

