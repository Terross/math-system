package com.mathsystem.graphapi.undirected;

import com.mathsystem.entity.graph.Color;
import com.mathsystem.entity.graph.Edge;
import com.mathsystem.entity.graph.Graph;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UndirectedGraph extends AbstractGraph {
    private List<List<UndirectedEdge>> adj;

    public UndirectedGraph(Graph graph) {
        this.vertexCount = graph.getVertexCount();
        this.edgeCount = graph.getEdgeCount();

        adj = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            adj.add(new ArrayList<>());
        }

        List<com.mathsystem.entity.graph.Vertex> vertices = graph.getVertexes();

        for (int i = 0; i < vertexCount; i++) {
            for (Edge edge: vertices.get(i).getOutgoingEdges()) {
                adj.get(i).add(new UndirectedEdge(
                        new Vertex(edge.getFromV(), edge.getFromVertex().getColor()),
                        new Vertex(edge.getFromV(), edge.getToVertex().getColor()),
                        edge.getWeight(),
                        edge.getColor()
                        ));
            }
        }
    }

    public UndirectedGraph(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        this.vertexCount = scanner.nextInt();
        this.edgeCount = scanner.nextInt();

        adj = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            Vertex v = new Vertex(
                    String.valueOf(scanner.nextInt()),
                    Color.valueOf(scanner.next().toUpperCase())
            );
            Vertex w = new Vertex(
                    String.valueOf(scanner.nextInt()),
                    Color.valueOf(scanner.next().toUpperCase())
            );
            validateVertex(v);
            validateVertex(w);

            double weight = scanner.nextDouble();
            Color color = Color.valueOf(scanner.next().toUpperCase());
            UndirectedEdge undirectedEdge = new UndirectedEdge(v, w, weight, color);

            adj.get(Integer.parseInt(v.getName())).add(undirectedEdge);
            adj.get(Integer.parseInt(w.getName())).add(undirectedEdge);
        }
    }

    public void setAdj(List<List<UndirectedEdge>> adj) {
        this.adj = adj;
    }

    public List<List<UndirectedEdge>> getAdj() {
        return adj;
    }
}
