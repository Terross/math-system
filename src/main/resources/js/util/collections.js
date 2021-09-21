export function deleteEdge(state, edge) {
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

    state.edgeCount = state.edgeCount - 1
}

