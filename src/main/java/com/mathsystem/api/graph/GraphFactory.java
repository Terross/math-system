package com.mathsystem.api.graph;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.graph.repository.Color;
import com.mathsystem.domain.graph.repository.GraphType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Integer.parseInt;

@Slf4j
public class GraphFactory {

    public static Graph loadGraphFromFile(File fileWithGraph) throws FileNotFoundException {

        Scanner scanner = new Scanner(fileWithGraph);
        Graph graph = Graph
                .builder()
                .directType(GraphType.valueOf(scanner.next()))
                .vertexCount(scanner.nextInt())
                .edgeCount(scanner.nextInt())
                .build();

        Map<UUID, Vertex> vertices = new HashMap<>();
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < graph.getVertexCount(); i++) {
            UUID id = UUID.fromString(scanner.next());
            Color color = Color.valueOf(scanner.next());
            String weight = scanner.next();
            String label = scanner.next();
            String xCoordinate = scanner.next();
            String yCoordinate = scanner.next();
            vertices.put(id, new Vertex(id, color,
                    weight.equals("null") ? null : parseInt(weight), label,
                    xCoordinate.equals("null") ? null : parseInt(xCoordinate),
                    yCoordinate.equals("null") ? null : parseInt(yCoordinate)));
        }

        for (int i = 0; i < graph.getEdgeCount(); i++) {
            UUID fromV = UUID.fromString(scanner.next());
            UUID toV = UUID.fromString(scanner.next());
            Color color = Color.valueOf(scanner.next());
            String weight = scanner.next();
            String label = scanner.next();
            edges.add(new Edge(fromV, toV, color,  weight.equals("null") ? null : parseInt(weight), label));
        }

        graph.setVertices(vertices);
        graph.setEdges(edges);

        return graph;
    }
}
