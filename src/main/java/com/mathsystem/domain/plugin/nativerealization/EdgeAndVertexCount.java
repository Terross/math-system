package com.mathsystem.domain.plugin.nativerealization;

import com.mathsystem.domain.plugin.GraphCharacteristic;
import com.mathsystem.lib.graphapi.AbstractGraph;

public class EdgeAndVertexCount implements GraphCharacteristic {
    @Override
    public Integer execute(AbstractGraph graph) {
        return graph.getEdgeCount() + graph.getVertexCount();
    }
}
