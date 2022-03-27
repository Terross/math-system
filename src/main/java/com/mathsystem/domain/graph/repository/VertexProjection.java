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
@EqualsAndHashCode(exclude = "graph")
@Table(name = "vertex")
@IdClass(VertexProjection.VertexPK.class)
public class VertexProjection {

    @Id
    private UUID id;

    @Id
    @JsonIgnore
    private UUID graph;

    private Color color;

    private Integer weight;

    private String label;

    @JsonProperty("xCoordinate")
    private Integer xCoordinate;

    @JsonProperty("yCoordinate")
    private Integer yCoordinate;

    @Data
    public static class VertexPK implements Serializable {
        private UUID id;
        private UUID graph;
    }
}
