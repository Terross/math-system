package com.mathsystem.domain.plugin;


import com.mathsystem.api.graph.model.Graph;

/**
 * Интерфейс свойства графа.
 * Требуется реализовать, если ваше цель - создать свойство
 */
public interface GraphProperty extends Plugin {
    boolean execute(Graph graph);
}
