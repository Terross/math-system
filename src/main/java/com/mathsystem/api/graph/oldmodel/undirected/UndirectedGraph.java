package com.mathsystem.api.graph.oldmodel.undirected;


import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.oldmodel.AbstractGraph;
import com.mathsystem.api.graph.oldmodel.Vertex;
import lombok.Data;
import java.util.*;

/**
 * Класс неориентированного графа
 */
@Data
public class UndirectedGraph extends AbstractGraph {

    public UndirectedGraph(Graph graph) {
        this.vertexCount = graph.getVertexCount();
        this.edgeCount = graph.getEdgeCount();
        this.vertices = new ArrayList<>();

        int index = 0;

        for (Map.Entry<UUID, com.mathsystem.api.graph.model.Vertex> entry: graph.getVertices().entrySet()) {
            this.vertices.add(new Vertex(
                    index++,
                    entry.getValue().getId().toString(),
                    entry.getValue().getColor(),
                    entry.getValue().getWeight(),
                    entry.getValue().getLabel(),
                    new LinkedList<>())
            );
        }

        int index2 = 0;
        for (Edge edge: graph.getEdges()) {

            Vertex fromV = this.findVertexById(edge.getFromV());
            Vertex toV = this.findVertexById(edge.getToV());

            UndirectedEdge undirectedEdge = new UndirectedEdge(
                    fromV,
                    toV,
                    edge.getWeight(),
                    edge.getColor(),
                    edge.getLabel(),
                    edge.getLabel()
            );

            fromV.getEdgeList().add(undirectedEdge);
            toV.getEdgeList().add(undirectedEdge);
        }
    }

    private Vertex findVertexById (UUID id) {
        return this.vertices
                .stream()
                .filter(vertex -> vertex.getName().equals(id.toString()))
                .findFirst().get();
    }
}
