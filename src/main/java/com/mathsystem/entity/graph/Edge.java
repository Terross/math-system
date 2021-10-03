package com.mathsystem.entity.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "edge")
@Data
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FROM_VERTEX_ID")
    @JsonIgnore
    private Vertex fromVertex;

    @ManyToOne
    @JoinColumn(name = "TO_VERTEX_ID")
    @JsonIgnore
    private Vertex toVertex;

    private Color color;
    private String fromV;
    private String toV;
    private Integer weight;
    private String label;
    private String name;

    @Override
    public String toString() {
        return fromV + " -> " + toV;
    }
}

