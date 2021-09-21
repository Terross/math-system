package com.mathsystem.graphapi;

import com.mathsystem.entity.graph.Edge;
import com.mathsystem.entity.graph.GraphType;
import com.mathsystem.entity.graph.Vertex;
import com.mathsystem.graphapi.directed.DirectedGraph;
import com.mathsystem.graphapi.undirected.UndirectedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GraphFactory {
    public static AbstractGraph createGraph(int vertexCount,
                                            int edgeCount,
                                            ArrayList<Edge> edges,
                                            GraphType graphType,
                                            List<Vertex> vertices) {
        AbstractGraph abstractGraph = null;

        switch (graphType) {
            case DIRECTED:
                abstractGraph = new DirectedGraph(
                        vertexCount,
                        edgeCount,
                        edges,
                        vertices);
                break;
            case UNDIRECTED:
                abstractGraph =  new UndirectedGraph(
                        vertexCount,
                        edgeCount,
                        edges,
                        vertices);
                break;
        }

        return abstractGraph;
    }

    public static UndirectedGraph loadUndirectedGraphFromFile(File file) throws FileNotFoundException {
        return new UndirectedGraph(file);
    }

    public static DirectedGraph loadDirectedGraphFromFile(File file) throws FileNotFoundException {
        return new DirectedGraph(file);
    }
}
