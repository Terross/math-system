package com.mathsystem.service;

import com.mathsystem.entity.task.PluginProjection;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.plugin.GraphCharacteristic;
import com.mathsystem.plugin.GraphProperty;
import com.mathsystem.plugin.Plugin;
import com.mathsystem.plugin.PluginFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GraphVerifier {

    private Map<String, Plugin> plugins;

    public String findForGraph(AbstractGraph abstractGraph, PluginProjection pluginProjection) {

        String fileName = pluginProjection.getFileName();
        int index = fileName.lastIndexOf('.');
        String name = fileName.substring(0, index);

        return switch (pluginProjection.isNative()
                ? plugins.get(pluginProjection.getName())
                : PluginFactory.loadPlugin(name)) {
            case GraphCharacteristic characteristic -> String.valueOf(characteristic.execute(abstractGraph));
            case GraphProperty property -> property.execute(abstractGraph) ? "Верно" : "Неверно";
            default -> throw new IllegalStateException("Unexpected value: " + PluginFactory.loadPlugin(name));
        };
        }
}
