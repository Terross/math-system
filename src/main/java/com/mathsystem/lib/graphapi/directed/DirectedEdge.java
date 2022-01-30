package com.mathsystem.lib.graphapi.directed;


import com.mathsystem.domain.task.graph.repository.Color;
import com.mathsystem.lib.graphapi.AbstractEdge;
import com.mathsystem.lib.graphapi.Vertex;

import java.util.List;

/**
 * Класс ориентированного ребра
 */
public class DirectedEdge extends AbstractEdge {

    /**
     * Конструктор - создание нового объекта ребра с определенными значениями
     * @param v - начальная вершина
     * @param w - целевая вершина
     * @see Vertex#Vertex(Integer, String, Color, Integer, String, List)
     * @param weight - вес ребра
     * @param color - цвет ребра
     * @param label - метка ребра
     */
    public DirectedEdge(
            Vertex v,
            Vertex w,
            Integer weight,
            Color color,
            String label
    ) {
        super(v, w, weight, color, label);
    }

    public Vertex from() {
        return v;
    }

    public Vertex to() {
        return w;
    }

    @Override
    public String toString() {
        return String.format("Edge = {" +
                "\n%s -> %s" +
                "\nweight =  %d" +
                "\ncolor = %s" +
                "\nlabel = %s",
                v.getName(), w.getName(), weight, color, label);
    }

}
