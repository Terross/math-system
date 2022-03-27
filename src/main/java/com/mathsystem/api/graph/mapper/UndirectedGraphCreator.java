//package com.mathsystem.api.graph.mapper;
//
//import com.mathsystem.api.graph.model.Edge;
//import com.mathsystem.api.graph.model.Graph;
//import com.mathsystem.api.graph.model.Vertex;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//public class UndirectedGraphCreator {
//
//    public Graph createUndirectedConnections(Graph graph) {
//        List<Vertex> vertexList = graph.getVertexList();
//        Map<UUID, Edge> edgeBuffer = new HashMap<>();
//        for (Vertex vertex : vertexList) {
//            for (Edge edge : vertex.getOutgoingEdges()) {
//                Edge addedEdge = Edge.builder()
//                        .id(edge.getId())
//                        .color(edge.getColor())
//                        .fromV(edge.getToV())
//                        .toV(edge.getFromV())
//                        .label(edge.getLabel())
//                        .weight(edge.getWeight())
//                        .build();
//                vertex.getIncomingEdges().add(addedEdge);
//                edgeBuffer.put(vertexList
//                        .stream()
//                        .filter(v -> v.getId().equals(edge.getToV()))
//                        .findFirst()
//                        .orElseThrow()
//                        .getId(), addedEdge);
//            }
//        }
//        vertexList.stream()
//                .filter(vertex -> edgeBuffer.get(vertex.getId()) != null)
//                .forEach(vertex -> vertex.getOutgoingEdges().add(edgeBuffer.get(vertex.getId())));
//        return graph;
//    }
//}
