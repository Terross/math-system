package com.mathsystem.graphapi;

import com.mathsystem.graphapi.undirected.UndirectedEdge;

import java.util.List;

public abstract class AbstractGraph {
    protected int vertexCount;
    protected int edgeCount;

    protected void validateVertex(Vertex vertex) {
        int v = Integer.parseInt(vertex.getName());
        if (v < 0 || v > vertexCount - 1) {
            throw  new IllegalArgumentException("Vertex " + v + " is not between 0 and " + (vertexCount - 1));
        }
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public void setEdgeCount(int edgeCount) {
        this.edgeCount = edgeCount;
    }

}
