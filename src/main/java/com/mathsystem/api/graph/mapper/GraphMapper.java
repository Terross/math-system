package com.mathsystem.api.graph.mapper;

import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.graph.repository.GraphProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = VertexMapper.class)
public interface GraphMapper {

    @Mapping(source = "vertexList", target = "vertices")
    @Mapping(source = "edgeList", target = "edges")
    Graph graphProjectionToGraph(GraphProjection graphProjection);
}
