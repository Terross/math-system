package com.mathsystem.entity.graph;

import com.mathsystem.entity.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Graph {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private GraphType graphType;

    private int vertexCount;
    private int edgeCount;

    @OneToMany(mappedBy = "graph", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vertex> vertexes;

    @OneToOne(mappedBy = "graph")
    @JsonIgnore
    private Task task;

    public void addVertex(Vertex vertex) {
        vertexCount++;
        vertex.setGraph(this);
        vertexes.add(vertex);
    }

    public void addEdge(Edge edge, Vertex from, Vertex to) {
        edgeCount++;
        edge.setToVertex(to);
        edge.setFromVertex(from);
        from.addOutgoingEdge(edge);
        to.addIncomingEdge(edge);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "id=" + id +
                ", vertexCount=" + vertexCount +
                ", edgeCount=" + edgeCount +
                ", vertexes=" + vertexes +
                //", task=" + task +
                '}';
    }
}
