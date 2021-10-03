package com.mathsystem.plugin;

import com.mathsystem.entity.graph.Graph;
import com.mathsystem.graphapi.AbstractGraph;

import java.io.File;
import java.util.List;

/**
 * Интерфейс характеристики графа.
 * Требуется реализовать, если ваше цель - создать характеристику
 */
public interface GraphCharacteristic extends Plugin {
    /**
     *
     * @param graph - граф с котороым требуется работать
     * @return возвращает целочисленное число
     * @see Integer
     * @see AbstractGraph
     * @see com.mathsystem.graphapi.directed.DirectedGraph#DirectedGraph(File)
     * @see com.mathsystem.graphapi.directed.DirectedGraph#DirectedGraph(int, int, List, List)
     * @see com.mathsystem.graphapi.undirected.UndirectedGraph#UndirectedGraph(File)
     * @see com.mathsystem.graphapi.undirected.UndirectedGraph#UndirectedGraph(int, int, List, List)
     */
    Integer execute(AbstractGraph graph);
}
