package com.mathsystem.domain.graph.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vertex")
public class VertexProjection {

    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private Color color;

    private Integer weight;

    private String label;

    @JsonProperty("xCoordinate")
    private Integer xCoordinate;

    @JsonProperty("yCoordinate")
    private Integer yCoordinate;
}
