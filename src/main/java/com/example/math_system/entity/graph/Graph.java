package com.example.math_system.entity.graph;

import com.example.math_system.entity.task.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Graph {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void setEdgeCount(int edgeCount) {
        this.edgeCount = edgeCount;
    }


    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexCount = vertexes.size();
        this.vertexes = vertexes;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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
