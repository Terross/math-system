package com.mathsystem.graphapi;

import com.mathsystem.domain.task.graph.repo.Color;
import lombok.Data;

import java.util.List;

/**
 * Абстрактный класс ребра
 * @see AbstractEdge#AbstractEdge(Vertex, Vertex, Integer, Color, String, String)
 */
@Data
public abstract class AbstractEdge implements Comparable<AbstractEdge> {
    /**Начальная вершина*/
    protected final Vertex v;
    /**Целевая вершина*/
    protected final Vertex w;
    /**Вес ребра*/
    protected final Integer weight;
    /**Цвет ребра*/
    protected final Color color;
    /**Метка ребра*/
    protected final String label;
    /**Имя ребра. Поле используется как индекс */
    protected final String name;

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
    public AbstractEdge(
            Vertex v,
            Vertex w,
            Integer weight,
            Color color,
            String label,
            String name
    ) {
        this.v = v;
        this.w = w;
        this.weight = weight;
        this.color = color;
        this.label = label;
        this.name = name;
    }

    /**
     *
     * @return одна из вершин данного ребра
     */
    public Vertex either() {
        return v;
    }

    public Vertex other(Vertex vertex) {
        if (vertex.getName().equals(v.getName())) {
            return w;
        } else {
            if (vertex.getName().equals(w.getName())) {
                return v;
            } else {
                throw new RuntimeException("Недопустимое ребро");
            }
        }
    }



    @Override
    public int compareTo(AbstractEdge abstractEdge) {
        return this.weight.compareTo(abstractEdge.getWeight());
    }
}
