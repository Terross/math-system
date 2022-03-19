package com.mathsystem.lib.graphapi.undirected;

import com.mathsystem.domain.graph.repository.Color;
import com.mathsystem.lib.graphapi.AbstractEdge;
import com.mathsystem.lib.graphapi.Vertex;

import java.util.List;

/**
 * Класс неориентированного ребра
 */
public class UndirectedEdge extends AbstractEdge {

    /**
     * Конструктор - создание нового объекта ребра с определенными значениями
     * @param v - начальная вершина
     * @param w - целевая вершина
     * @see Vertex#Vertex(Integer, String, Color, Integer, String, List)
     * @param weight - вес ребра
     * @param color - цвет ребра
     * @param label - метка ребра
     */
    public UndirectedEdge(
            Vertex v,
            Vertex w,
            Integer weight,
            Color color,
            String label
    ) {
        super(v, w, weight, color, label);
    }

    @Override
    public String toString() {
        return String.format("Edge = {" +
                        "\n%s <-> %s" +
                        "\nweight =  %d" +
                        "\ncolor = %s" +
                        "\nlabel = %s",
                 v.getName(), w.getName(), weight, color, label);
    }
}
