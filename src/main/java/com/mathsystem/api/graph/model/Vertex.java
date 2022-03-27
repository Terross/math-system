package com.mathsystem.api.graph.model;

import com.mathsystem.domain.graph.repository.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vertex {

    private Color color;

    private Integer weight;

    private String label;

    private Integer xCoordinate;

    private Integer yCoordinate;
}
