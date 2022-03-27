package com.mathsystem.domain.plugin.plugintype;


import com.mathsystem.api.graph.model.Graph;

/**
 * Интерфейс характеристики графа.
 * Требуется реализовать, если ваше цель - создать характеристику
 */
public interface GraphCharacteristic extends Plugin {
    Integer execute(Graph graph);
}
