package com.mathsystem.api.graph.model;

import com.mathsystem.domain.graph.repository.Color;
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

    private UUID fromV;

    private UUID toV;

    private Color color;

    private Integer weight;

    private String label;
}
