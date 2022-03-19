package com.mathsystem.domain.graph.repository;

import com.mathsystem.domain.task.repository.TaskProjection;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class GraphProjection {

    @Id
    @GeneratedValue
    private Long id;

    private GraphType graphType;

    private int vertexCount;

    private int edgeCount;

    @OneToMany(mappedBy = "graphProjection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VertexProjection> vertexProjections;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_id")
    private TaskProjection taskProjection;

    public void addVertex(VertexProjection vertexProjection) {
        vertexCount++;
        vertexProjection.setGraphProjection(this);
        vertexProjections.add(vertexProjection);
    }

    public void addEdge(EdgeProjection edgeProjection, VertexProjection from, VertexProjection to) {
        edgeCount++;
        edgeProjection.setToVertexProjection(to);
        edgeProjection.setFromVertexProjection(from);
        from.addOutgoingEdge(edgeProjection);
        to.addIncomingEdge(edgeProjection);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "id=" + id +
                ", vertexCount=" + vertexCount +
                ", edgeCount=" + edgeCount +
                ", vertexes=" + vertexProjections +
                //", task=" + task +
                '}';
    }
}
