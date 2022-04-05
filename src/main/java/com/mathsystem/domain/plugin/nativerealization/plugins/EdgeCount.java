package com.mathsystem.domain.plugin.nativerealization.plugins;

import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;
import org.springframework.stereotype.Component;

@Component
public class EdgeCount implements GraphCharacteristic {
    @Override
    public Integer execute(Graph graph) {
        return graph.getEdgeCount();
    }
}
