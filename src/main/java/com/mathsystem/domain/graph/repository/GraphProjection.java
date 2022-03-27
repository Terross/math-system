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
    @GeneratedValue
    private UUID id;
    private GraphType directType;
    private int vertexCount;
    private int edgeCount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private List<VertexProjection> vertexList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "graph_id")
    private List<EdgeProjection> edgeList;
}
