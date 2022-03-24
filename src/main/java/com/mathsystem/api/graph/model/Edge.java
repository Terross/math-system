package com.mathsystem.api.graph.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Edge {

    private UUID id;

    private UUID fromV;

    private UUID toV;

    private Color color;

    private Integer weight;

    private String label;
}
