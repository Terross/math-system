package com.mathsystem.api.graph.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mathsystem.domain.graph.repository.Color;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Vertex {

    private UUID id;

    private Color color;

    private Integer weight;

    private String label;

    @JsonProperty("xCoordinate")
    private Integer xCoordinate;

    @JsonProperty("yCoordinate")
    private Integer yCoordinate;

    private List<Edge> outgoingEdges;

    private List<Edge> incomingEdges;
}
