package com.mathsystem.api.graph.oldmodel;

import com.mathsystem.domain.graph.repository.Color;
import lombok.Data;

import java.util.List;


@Data
public class Vertex {
    private final Integer index;
    /**Имя вершины. Поле используется как индекс */
    private final String name;
    /**Цвет вершины */
    private final Color color;
    /**Вес вершины */
    private final Integer weight;
    /**Метка для дополнительной информации о вершине */
    private final String label;
    /**Список выходящих из вершины ребер */
    private List<AbstractEdge> edgeList;

    public Vertex(
            Integer index,
            String name,
            Color color,
            Integer weight,
            String label,
            List<AbstractEdge> edgeList
    ) {
        this.index = index;
        this.color = color;
        this.name = name;
        this.weight = weight;
        this.label = label;
        this.edgeList = edgeList;
    }
}
