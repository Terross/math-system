package com.mathsystem.domain.plugin.nativerealization;

import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.plugin.PluginRunner;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;
import com.mathsystem.domain.plugin.plugintype.GraphProperty;
import com.mathsystem.domain.plugin.plugintype.Plugin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NativePluginService implements PluginRunner {
    private final Map<String, Plugin> nativePlugins;

    public String runPlugin(String name, Graph graph) {

        return switch (nativePlugins.get(name)) {
            case GraphCharacteristic characteristic -> String.valueOf(characteristic.execute(graph));
            case GraphProperty property -> property.execute(graph) ? "верно": "неверно";
            default -> "Ошибка надо обработать";
        };
    }
}
