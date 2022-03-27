package com.mathsystem.domain.graph.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "edge")
public class EdgeProjection {

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private Color color;

    private Integer weight;

    private String label;

    private UUID fromV;

    private UUID toV;
}

