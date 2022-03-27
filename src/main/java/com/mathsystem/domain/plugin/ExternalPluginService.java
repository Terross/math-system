package com.mathsystem.domain.plugin;

import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalPluginService implements PluginRunner{

    private final PluginFactory pluginFactory;


    @Override
    public String runPlugin(String name, Graph graph) {
        return switch (pluginFactory.loadPlugin(name)) {
            case GraphCharacteristic characteristic -> String.valueOf(characteristic.execute(graph));
            case GraphProperty property -> property.execute(graph) ? "верно": "неверно";
            default -> "Ошибка надо обработать";
        };
    }
}

