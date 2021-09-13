package com.mathsystem.graphapi.directed;

import com.mathsystem.entity.graph.Color;
import com.mathsystem.graphapi.AbstractEdge;
import com.mathsystem.graphapi.Vertex;

public class DirectedEdge extends AbstractEdge {

    public DirectedEdge(Vertex v, Vertex w, double weight, Color color) {
        super(v, w, weight, color);
    }

    @Override
    public String toString() {
        return String.format("%s -> %s %.2f, color = %s", v, w, weight, color);
    }
}
