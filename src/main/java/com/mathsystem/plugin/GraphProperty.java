package com.mathsystem.plugin;

import com.mathsystem.entity.graph.Graph;
import com.mathsystem.graphapi.AbstractGraph;

import java.io.File;
import java.util.List;

/**
 * Интерфейс свойства графа.
 * Требуется реализовать, если ваше цель - создать свойство
 */
public interface GraphProperty extends Plugin {

    /**
     *
     * @param graph - граф с котороым требуется работать
     * @return возвращает булевое значение
     * @see Boolean
     * @see AbstractGraph
     * @see com.mathsystem.graphapi.directed.DirectedGraph#DirectedGraph(File)
     * @see com.mathsystem.graphapi.directed.DirectedGraph#DirectedGraph(int, int, List, List)
     * @see com.mathsystem.graphapi.undirected.UndirectedGraph#UndirectedGraph(File)
     * @see com.mathsystem.graphapi.undirected.UndirectedGraph#UndirectedGraph(int, int, List, List)
     */
    boolean execute(AbstractGraph graph);
}
