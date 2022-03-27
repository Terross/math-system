package com.mathsystem.api.graph.mapper;

import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.domain.graph.repository.GraphProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GraphMapper {

    Graph graphProjectionToGraph(GraphProjection graphProjection);
    GraphProjection graphToGraphProjection(Graph graph);
}
