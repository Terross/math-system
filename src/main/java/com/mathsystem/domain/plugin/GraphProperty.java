package com.mathsystem.domain.plugin;


import com.mathsystem.lib.graphapi.AbstractGraph;

/**
 * Интерфейс свойства графа.
 * Требуется реализовать, если ваше цель - создать свойство
 */
public interface GraphProperty extends Plugin {
    boolean execute(AbstractGraph graph);
}
