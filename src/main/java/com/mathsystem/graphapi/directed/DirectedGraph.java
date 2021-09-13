package com.mathsystem.graphapi.directed;

import com.mathsystem.entity.graph.Color;
import com.mathsystem.entity.graph.Edge;
import com.mathsystem.entity.graph.Graph;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;
import com.mathsystem.graphapi.undirected.UndirectedEdge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DirectedGraph extends AbstractGraph {
    private List<List<DirectedEdge>> adj;

    public DirectedGraph(Graph graph) {
        this.vertexCount = graph.getVertexCount();
        this.edgeCount = graph.getEdgeCount();

        adj = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            adj.add(new ArrayList<>());
        }

        List<com.mathsystem.entity.graph.Vertex> vertices = graph.getVertexes();

        for (int i = 0; i < vertexCount; i++) {
            for (Edge edge: vertices.get(i).getOutgoingEdges()) {
                adj.get(i).add(new DirectedEdge(
                        new Vertex(edge.getFromVertex().getColor(), edge.getFromV()),
                        new Vertex(edge.getToVertex().getColor(), edge.getFromV()),
                        edge.getWeight(),
                        edge.getColor()
                ));
            }
        }
    }

    public DirectedGraph(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        this.vertexCount = scanner.nextInt();
        this.edgeCount = scanner.nextInt();

        adj = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            Vertex v = new Vertex(
                    Color.valueOf(scanner.next().toUpperCase()),
                    String.valueOf(scanner.nextInt())
            );
            Vertex w = new Vertex(
                    Color.valueOf(scanner.next().toUpperCase()),
                    String.valueOf(scanner.nextInt())
            );
            validateVertex(v);
            validateVertex(w);

            double weight = scanner.nextDouble();
            Color color = Color.valueOf(scanner.next().toUpperCase());
            DirectedEdge directedEdge = new DirectedEdge(v, w, weight, color);

            adj.get(Integer.parseInt(v.getName())).add(directedEdge);
        }
    }

    public void setAdj(List<List<DirectedEdge>> adj) {
        this.adj = adj;
    }

    public List<List<DirectedEdge>> getAdj() {
        return adj;
    }
}
