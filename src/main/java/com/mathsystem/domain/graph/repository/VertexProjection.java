package com.mathsystem.domain.graph.repository;

import com.mathsystem.api.graph.model.Color;
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
@Table(name = "vertex")
public class VertexProjection {
    @Id
    @GeneratedValue
    private UUID id;

    private Color color;

    private Integer weight;

    private String label;

    private Integer xCoordinate;

    private Integer yCoordinate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "from_v")
    private List<EdgeProjection> outgoingEdgeProjections;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "to_v")
    private List<EdgeProjection> incomingEdgeProjections;

}
