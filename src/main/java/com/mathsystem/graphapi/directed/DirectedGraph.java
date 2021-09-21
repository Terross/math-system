package com.mathsystem.graphapi.directed;

import com.mathsystem.entity.graph.Color;
import com.mathsystem.entity.graph.Edge;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DirectedGraph extends AbstractGraph {
    private List<List<DirectedEdge>> adj;

    public DirectedGraph(int vertexCount,
                         int edgeCount,
                         ArrayList<Edge> edges,
                         List<com.mathsystem.entity.graph.Vertex> vertices) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.vertices = new HashMap<>();
        this.adj = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            adj.add(new ArrayList<>());
            com.mathsystem.entity.graph.Vertex v =  vertices.get(i);
            String vertexName = v.getName();
            Color vertexColor = v.getColor();
            this.vertices.put(
                    vertexName,
                    new Vertex(vertexName, vertexColor));
        }

        for (Edge edge: edges) {
            DirectedEdge directedEdge= new DirectedEdge(
                    this.vertices.get(edge.getFromV()),
                    this.vertices.get(edge.getToV()),
                    edge.getWeight() == null ? 0 : edge.getWeight(),
                    edge.getColor()
            );
            adj.get(Integer.parseInt(edge.getFromVertex().getName())).add(directedEdge);
        }
    }

    public DirectedGraph(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        this.vertexCount = scanner.nextInt();
        this.edgeCount = scanner.nextInt();
        this.vertices = new HashMap<>();
        this.adj = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            adj.add(new ArrayList<>());
            String vertexName = scanner.next();
            Color vertexColor = Color.valueOf(scanner.next().toLowerCase());
            this.vertices.put(
                    vertexName,
                    new Vertex(vertexName, vertexColor)
            );
        }

        for (int i = 0; i < edgeCount; i++) {
            Vertex v = this.vertices.get(scanner.next());
            Vertex w = this.vertices.get(scanner.next());
//            validateVertex(v);
//            validateVertex(w);

            double weight = scanner.nextDouble();
            Color color = Color.valueOf(scanner.next().toLowerCase());
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

    @Override
    public String toString() {
        return "DirectedGraph: " + super.toString() +
                "\nEdges: " + adj;
    }
}
