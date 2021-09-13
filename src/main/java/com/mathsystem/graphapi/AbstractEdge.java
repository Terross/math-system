package com.mathsystem.graphapi;

import com.mathsystem.entity.graph.Color;
import com.mathsystem.graphapi.undirected.UndirectedEdge;

public abstract class AbstractEdge implements Comparable<UndirectedEdge> {
    protected final Vertex v;
    protected final Vertex w;
    protected final double weight;
    protected final Color color;

    public AbstractEdge(Vertex v, Vertex w, double weight, Color color) {
        this.v = v;
        this.w = w;
        this.weight = weight;
        this.color = color;
    }

    @Override
    public int compareTo(UndirectedEdge undirectedEdge) {
        return Double.compare(this.weight, undirectedEdge.getWeight());
    }

    public double getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }

    public Vertex getV() {
        return v;
    }

    public Vertex getW() {
        return w;
    }
}
