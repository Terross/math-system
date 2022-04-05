package com.mathsystem.api.graph.oldmodel;

import lombok.Data;
import java.util.List;

/**
 * Абстрактный класс графа
 */
@Data
public abstract class AbstractGraph {
    /**Количество вершин в графе*/
    protected int vertexCount;
    /**Количество ребер в графе*/
    protected int edgeCount;
    /**Список вершин Vertex */
    protected List<Vertex> vertices;

}
