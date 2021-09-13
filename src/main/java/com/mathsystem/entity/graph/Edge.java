package com.mathsystem.entity.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "edge")
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

    private Double weight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vertex getFromVertex() {
        return fromVertex;
    }

    public void setFromVertex(Vertex fromVertex) {
        this.fromVertex = fromVertex;
    }

    public Vertex getToVertex() {
        return toVertex;
    }

    public void setToVertex(Vertex toVertex) {
        this.toVertex = toVertex;
    }

    public String getFromV() {
        return fromV;
    }

    public void setFromV(String fromV) {
        this.fromV = fromV;
    }

    public String getToV() {
        return toV;
    }

    public void setToV(String toV) {
        this.toV = toV;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return fromV + " -> " + toV;
    }
}

