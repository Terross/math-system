package com.mathsystem.api.graph.mapper;

import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.graph.repository.VertexProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VertexMapper {

    Vertex vertexProjectionToVertex(VertexProjection vertexProjection);

    VertexProjection vertexToVertexProjection(Vertex vertex);

    List<Vertex> vertexProjectionListToVertexList(List<VertexProjection> vertexProjectionList);

    List<VertexProjection> vertexListToVertexProjectionList(List<Vertex> vertexList);
}
