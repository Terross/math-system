package com.mathsystem.api.graph.mapper;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.domain.graph.repository.EdgeProjection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EdgeMapper {

    Edge edgeProjectionToEdge(EdgeProjection edgeProjection);
    EdgeProjection edgeToEdgeProjection(Edge edge);

    List<Edge> edgeProjectionListToEdgeList(List<EdgeProjection> edgeProjections);
    List<EdgeProjection> edgeListToEdgeProjectionList(List<Edge> edges);
}
