package com.mathsystem.domain.graph.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "graph")
public class GraphProjection {

    @Id
    private UUID id;
    private GraphType directType;
    private int vertexCount;
    private int edgeCount;

    @OneToMany(mappedBy = "graph", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VertexProjection> vertexList;

    @OneToMany(mappedBy = "graph", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EdgeProjection> edgeList;

    public void prepareToSave() {
        id = UUID.randomUUID();
        vertexList.forEach(vertex -> vertex.setGraph(id));
        edgeList.forEach(edge -> edge.setGraph(id));
    }
}
