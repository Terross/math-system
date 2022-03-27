package com.mathsystem.domain.plugin;

import com.mathsystem.api.graph.model.Graph;

public interface PluginRunner {
    String runPlugin(String name, Graph graph);
}
