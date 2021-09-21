package com.mathsystem.graphapi.undirected;

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

public class UndirectedGraph extends AbstractGraph {
    private List<List<UndirectedEdge>> adj;

    public UndirectedGraph(int vertexCount,
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
            UndirectedEdge undirectedEdge = new UndirectedEdge(
                    this.vertices.get(edge.getFromV()),
                    this.vertices.get(edge.getToV()),
                    edge.getWeight() == null ? 0 : edge.getWeight(),
                    edge.getColor()
            );
            adj.get(Integer.parseInt(edge.getFromVertex().getName())).add(undirectedEdge);
            adj.get(Integer.parseInt(edge.getToVertex().getName())).add(undirectedEdge);
        }
    }

    public UndirectedGraph(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        this.vertexCount = scanner.nextInt();
        this.edgeCount = scanner.nextInt();
        this.vertices = new HashMap<>();
        this.adj = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            this.adj.add(new ArrayList<>());
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
            UndirectedEdge undirectedEdge = new UndirectedEdge(v, w, weight, color);

            this.adj.get(Integer.parseInt(v.getName())).add(undirectedEdge);
            this.adj.get(Integer.parseInt(w.getName())).add(undirectedEdge);
        }
    }

    public void setAdj(List<List<UndirectedEdge>> adj) {
        this.adj = adj;
    }

    public List<List<UndirectedEdge>> getAdj() {
        return adj;
    }

    @Override
    public String toString() {
        return "UndirectedGraph: " + super.toString() +
                "\nEdges: " + adj;
    }
}
