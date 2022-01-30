package com.mathsystem.domain.plugin;


import com.mathsystem.lib.graphapi.AbstractGraph;

/**
 * Интерфейс характеристики графа.
 * Требуется реализовать, если ваше цель - создать характеристику
 */
public interface GraphCharacteristic extends Plugin {
    Integer execute(AbstractGraph graph);
}
