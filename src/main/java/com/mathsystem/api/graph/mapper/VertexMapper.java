package com.mathsystem.api.graph.mapper;

import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.graph.repository.VertexProjection;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class VertexMapper {

    Map<UUID, Vertex> vertexProjectionListToVertexMap(List<VertexProjection> vertexProjectionList) {
        Map<UUID, Vertex> result = new HashMap<>();
        vertexProjectionList.forEach(vertexProjection -> result
                .put(vertexProjection.getId(),
                        Vertex
                                .builder()
                                .id(vertexProjection.getId())
                                .color(vertexProjection.getColor())
                                .label(vertexProjection.getLabel())
                                .weight(vertexProjection.getWeight())
                                .xCoordinate(vertexProjection.getXCoordinate())
                                .yCoordinate(vertexProjection.getYCoordinate())
                                .build()));
        return result;
    }
}
