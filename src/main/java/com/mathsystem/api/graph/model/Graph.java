package com.mathsystem.api.graph.model;

import com.mathsystem.domain.graph.repository.GraphType;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class Graph {

    private GraphType directType;

    private Integer vertexCount;

    private Integer edgeCount;

    private Map<UUID, Vertex> vertices;

    private List<Edge> edges;
}
