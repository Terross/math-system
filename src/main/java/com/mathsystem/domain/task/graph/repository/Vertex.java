package com.mathsystem.domain.task.graph.repo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathsystem.domain.task.graph.repo.Color;
import com.mathsystem.domain.task.graph.repo.Edge;
import com.mathsystem.domain.task.graph.repo.Graph;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "vertex")
public class Vertex {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private Color color;

    private Integer weight;

    private String label;

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
