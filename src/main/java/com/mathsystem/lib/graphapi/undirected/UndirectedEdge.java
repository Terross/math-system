package com.mathsystem.graphapi.undirected;

import com.mathsystem.domain.task.graph.repo.Color;
import com.mathsystem.graphapi.AbstractEdge;
import com.mathsystem.graphapi.Vertex;

import java.util.List;

/**
 * Класс неориентированного ребра
 */
public class UndirectedEdge extends AbstractEdge   {

    /**
     * Конструктор - создание нового объекта ребра с определенными значениями
     * @param v - начальная вершина
     * @param w - целевая вершина
     * @see Vertex#Vertex(Integer, String, Color, Integer, String, List)
     * @param weight - вес ребра
     * @param color - цвет ребра
     * @param label - метка ребра
     * @param name - имя ребра
     */
    public UndirectedEdge(
            Vertex v,
            Vertex w,
            Integer weight,
            Color color,
            String label,
            String name
    ) {
        super(v, w, weight, color, label, name);
    }

    @Override
    public String toString() {
        return String.format("Edge %s = {" +
                        "\n%s <-> %s" +
                        "\nweight =  %d" +
                        "\ncolor = %s" +
                        "\nlabel = %s",
                name, v.getName(), w.getName(), weight, color, label);
    }
}
