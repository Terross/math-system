package com.example.mathsystem.entity.graph;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vertex")
public class Vertex {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private String color;

    @OneToMany(mappedBy = "fromVertex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edge> outgoingEdges;

    @OneToMany(mappedBy = "toVertex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edge> incomingEdges;

    @ManyToOne
    @JoinColumn(name = "GRAPH_ID")
    @JsonIgnore
    private Graph graph;

    public void addOutgoingEdge(Edge edge) {
        outgoingEdges.add(edge);
    }

    public void addIncomingEdge(Edge edge) {
        incomingEdges.add(edge);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    public void setOutgoingEdges(List<Edge> outgoingEdges) {
        this.outgoingEdges = outgoingEdges;
    }

    public List<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    public void setIncomingEdges(List<Edge> incomingEdges) {
        this.incomingEdges = incomingEdges;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", outgoingEdges=" + outgoingEdges +
                ", incomingEdges=" + incomingEdges +
                //", graph=" + graph.getId() +
                '}';
    }
}
