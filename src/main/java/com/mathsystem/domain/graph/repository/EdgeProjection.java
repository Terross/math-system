package com.mathsystem.domain.graph.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mathsystem.api.graph.model.Color;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdgeProjection {

    @Id
    @GeneratedValue
    private UUID pk;

    private UUID id;

    private Color color;

    private Integer weight;

    private String label;

}

