package com.mathsystem.domain.task.graph;


import com.mathsystem.domain.plugin.GraphCharacteristic;
import com.mathsystem.domain.plugin.GraphProperty;
import com.mathsystem.domain.plugin.Plugin;
import com.mathsystem.domain.plugin.PluginFactory;
import com.mathsystem.domain.plugin.repository.PluginProjection;
import com.mathsystem.lib.graphapi.AbstractGraph;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GraphVerifier {

//    private Map<String, Plugin> plugins;

    public String findForGraph(AbstractGraph abstractGraph, PluginProjection pluginProjection) {

//        String fileName = pluginProjection.getFileName();
//        int index = fileName.lastIndexOf('.');
//        String name = fileName.substring(0, index);
//
//        return switch (pluginProjection.isNative()
//                ? plugins.get(pluginProjection.getName())
//                : PluginFactory.loadPlugin(name)) {
//            case GraphCharacteristic characteristic -> String.valueOf(characteristic.execute(abstractGraph));
//            case GraphProperty property -> property.execute(abstractGraph) ? "Верно" : "Неверно";
//            default -> throw new IllegalStateException("Unexpected value: " + PluginFactory.loadPlugin(name));
//        };
        return null;
    }
}
