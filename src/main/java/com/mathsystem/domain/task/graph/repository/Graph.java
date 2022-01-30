package com.mathsystem.domain.task.graph.repo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathsystem.entity.task.Task;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Graph {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
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
