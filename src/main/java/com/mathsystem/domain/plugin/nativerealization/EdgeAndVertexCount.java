package com.mathsystem.domain.plugin.nativerealization;

import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.GraphCharacteristic;

public class EdgeAndVertexCount implements GraphCharacteristic {
    @Override
    public Integer execute(Graph graph) {
        return graph.getEdgeCount() + graph.getVertexCount();
    }
}
