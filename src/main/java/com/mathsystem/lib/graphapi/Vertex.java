package com.mathsystem.lib.graphapi;

import com.mathsystem.domain.graph.repository.Color;
import lombok.Data;

import java.util.List;

/**
 * Класс вершины в графе
 * @see Vertex#Vertex(Integer, String, Color, Integer, String, List)
 */
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
    /**
     * Конструктор - создание нового объекта вершины с определенными значениями
     * @param index - производитель
     * @param label - цена
     * @param color - цвет
     * @param weight - вес
     * @param name - имя
     * @param edgeList - список выходящих ребер @see AbstractEdge
     * @see AbstractEdge#AbstractEdge(Vertex, Vertex, Integer, Color, String, String)
     */
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

    @Override
    public String toString() {
        return String.format("\nVertex %s = {" +
                "\ncolor = %s" +
                "\nweight = %s" +
                "\nlabel = %s" +
                "\nedgeList = %s",
                name, color, weight, label, edgeList);
    }
}
