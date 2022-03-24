package com.mathsystem.api.graph.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.JoinColumn;
import java.util.List;

@Data
public class Graph {

    @JsonProperty("directType")
    private GraphType graphType;

    private Integer vertexCount;

    private Integer edgeCount;

    private List<Vertex> vertexList;
}
