package com.example.math_system.entity.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "edge")
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FROM_VERTEX_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Vertex fromVertex;

    @ManyToOne
    @JoinColumn(name = "TO_VERTEX_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Vertex toVertex;

    @Column(name = "FROM_VERTEX_ID")
    private Long from;
    @Column(name = "TO_VERTEX_ID")
    private Long to;

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

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}

