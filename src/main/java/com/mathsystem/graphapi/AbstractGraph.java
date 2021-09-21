package com.mathsystem.graphapi;
import java.util.List;
import java.util.Map;

public abstract class AbstractGraph {
    protected int vertexCount;
    protected int edgeCount;
    protected Map<String, Vertex> vertices;

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

    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(Map<String, Vertex> vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        return  "\nvertexCount: " + vertexCount +
                "\nedgeCount: " + edgeCount +
                "\nvertices: " + vertices;
    }
}
