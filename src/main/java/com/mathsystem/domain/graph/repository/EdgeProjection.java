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
@EqualsAndHashCode(exclude = "graph")
@IdClass(EdgeProjection.EdgePK.class)
public class EdgeProjection {

    @Id
    private UUID id;

    @Id
    @JsonIgnore
    private UUID graph;

    private Color color;

    private Integer weight;

    private String label;

    private UUID fromV;

    private UUID toV;

    @Data
    public static class EdgePK implements Serializable {
        private UUID id;
        private UUID graph;
    }
}

