package com.mathsystem.graphapi;

import com.mathsystem.entity.graph.Edge;
import com.mathsystem.entity.graph.GraphType;
import com.mathsystem.entity.graph.Vertex;
import com.mathsystem.graphapi.directed.DirectedGraph;
import com.mathsystem.graphapi.undirected.UndirectedGraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Клас фабрики для создания графов
 */
public class GraphFactory {
    /**
     * Статическая функция создающая граф (используется в контроллеерах)
     * @param vertexCount - количество вершин в графе
     * @param edgeCount - количество ребер в графе
     * @param edges - список ребер
     * @param graphType - тип графа, который надо вернуть
     * @param vertices - список вершин
     * @return возвращает тип AbstractGraph
     */
    public static AbstractGraph createGraph(int vertexCount,
                                            int edgeCount,
                                            List<Edge> edges,
                                            GraphType graphType,
                                            List<Vertex> vertices) {
        AbstractGraph abstractGraph = null;

        switch (graphType) {
            case DIRECTED:
                abstractGraph = new DirectedGraph(
                        vertexCount,
                        edgeCount,
                        edges,
                        vertices);
                break;
            case UNDIRECTED:
                abstractGraph =  new UndirectedGraph(
                        vertexCount,
                        edgeCount,
                        edges,
                        vertices);
                break;
        }

        return abstractGraph;
    }

    /**
     * Функция загружает граф из файла
     * @param file - объект файла с графом
     * @param graphType - тип, который требуется загрузить
     * @return возвращает тип AbstractGraph#AbstractGraph()
     * @throws FileNotFoundException
     */
    public static AbstractGraph loadGraphFromFile(File file, GraphType graphType)
            throws FileNotFoundException {
        AbstractGraph abstractGraph = null;
        switch (graphType) {
            case DIRECTED:
                abstractGraph =  new DirectedGraph(file);
                break;
            case UNDIRECTED:
                abstractGraph = new UndirectedGraph(file);
                break;
        }
        return abstractGraph;
    }

    /**
     * Функция загружает неориентированный граф из файла
     * @param file - объект файла с графом
     * @return возвращает тип AbstractGraph
     * @throws FileNotFoundException
     */
    public static UndirectedGraph loadUndirectedGraphFromFile(File file) {
        return new UndirectedGraph(file);
    }

    /**
     * Функция загружает ориентированный граф из файла
     * @param file - объект файла с графом
     * @return возвращает тип AbstractGraph
     * @throws FileNotFoundException
     */
    public static DirectedGraph loadDirectedGraphFromFile(File file) {
        return new DirectedGraph(file);
    }
}
