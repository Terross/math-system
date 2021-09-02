package com.example.math_system.plugin;

import com.example.math_system.entity.graph.Graph;

public interface Plugin {
    Double execute(PluginLoader pluginLoader, Graph graph);
}
