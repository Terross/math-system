package com.mathsystem.graphapi.undirected;

import com.mathsystem.entity.graph.Color;
import com.mathsystem.graphapi.AbstractEdge;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;

public class UndirectedEdge extends AbstractEdge   {

    public UndirectedEdge(Vertex v, Vertex w, double weight, Color color) {
        super(v, w, weight, color);
    }

    @Override
    public String toString() {
        return String.format("%s <-> %s %.2f (%s)\n", v, w, weight, color);
    }
}
