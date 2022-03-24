package com.mathsystem.api.graph.mapper;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.graph.repository.EdgeProjection;
import com.mathsystem.domain.graph.repository.GraphProjection;
import com.mathsystem.domain.graph.repository.VertexProjection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphMapper {
    public Graph graphProjectionToGraph(GraphProjection graphProjection) {
        return null;
    }

    public GraphProjection graphToGraphProjection(Graph graph) {
        return GraphProjection.builder()
                .directType(graph.getGraphType())
                .vertexCount(graph.getVertexCount())
                .edgeCount(graph.getEdgeCount())
                .vertexProjectionList(vertexListToVertexProjectionList(graph.getVertexList()))
                .build();
    }

    private List<VertexProjection> vertexListToVertexProjectionList(List<Vertex> vertexList) {
        return vertexList.stream()
                .map(vertex -> VertexProjection.builder()
                        .color(vertex.getColor())
                        .label(vertex.getLabel())
                        .weight(vertex.getWeight())
                        .incomingEdgeProjections(edgeListToVertexProjectionList(vertex.getIncomingEdges()))
                        .outgoingEdgeProjections(edgeListToVertexProjectionList(vertex.getOutgoingEdges()))
                        .xCoordinate(vertex.getXCoordinate())
                        .yCoordinate(vertex.getYCoordinate())
                        .build())
                .toList();
    }

    private List<EdgeProjection> edgeListToVertexProjectionList(List<Edge> edgeList) {
        return edgeList.stream()
                .map(edge -> EdgeProjection.builder()
                        .color(edge.getColor())
                        .id(edge.getId())
                        .weight(edge.getWeight())
                        .label(edge.getLabel())
                        .build())
                .toList();
    }

}
