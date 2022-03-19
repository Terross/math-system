package com.mathsystem.domain.graph.repository;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "vertex")
public class VertexProjection {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    private Color color;

    private Integer weight;

    private String label;

    @OneToMany(mappedBy = "fromVertexProjection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EdgeProjection> outgoingEdgeProjections;

    @OneToMany(mappedBy = "toVertexProjection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EdgeProjection> incomingEdgeProjections;

    @ManyToOne
    @JoinColumn(name = "graph_id")
    @JsonIgnore
    private GraphProjection graphProjection;

    public void addOutgoingEdge(EdgeProjection edgeProjection) {
        outgoingEdgeProjections.add(edgeProjection);
    }

    public void addIncomingEdge(EdgeProjection edgeProjection) {
        incomingEdgeProjections.add(edgeProjection);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", outgoingEdges=" + outgoingEdgeProjections +
                ", incomingEdges=" + incomingEdgeProjections +
                //", graph=" + graph.getId() +
                '}';
    }
}
