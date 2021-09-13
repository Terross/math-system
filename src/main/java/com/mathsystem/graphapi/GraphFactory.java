package com.mathsystem.graphapi;

import com.mathsystem.entity.graph.Graph;
import com.mathsystem.graphapi.directed.DirectedGraph;
import com.mathsystem.graphapi.undirected.UndirectedGraph;

import java.io.File;
import java.io.FileNotFoundException;

public class GraphFactory {
    public static AbstractGraph createGraph(Graph graph) {
        AbstractGraph abstractGraph = null;

        switch (graph.getGraphType()) {
            case DIRECTED:
                abstractGraph = new DirectedGraph(graph);
                break;
            case UNDIRECTED:
                abstractGraph =  new UndirectedGraph(graph);
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
