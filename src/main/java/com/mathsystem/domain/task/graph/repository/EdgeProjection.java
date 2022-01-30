package com.mathsystem.domain.task.graph.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class EdgeProjection {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "from_vertex_id")
    @JsonIgnore
    private VertexProjection fromVertexProjection;

    @ManyToOne
    @JoinColumn(name = "to_vertex_id")
    @JsonIgnore
    private VertexProjection toVertexProjection;

    private Color color;

    private String fromV;

    private String toV;

    private Integer weight;

    private String label;

    @Override
    public String toString() {
        return fromV + " -> " + toV;
    }
}

