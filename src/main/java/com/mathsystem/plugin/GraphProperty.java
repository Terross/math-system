package com.mathsystem.plugin;

import com.mathsystem.entity.graph.Graph;
import com.mathsystem.graphapi.AbstractGraph;

public interface GraphProperty extends Plugin {
    boolean execute(Graph graph);
}
