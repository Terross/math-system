package com.mathsystem.graphapi;
import com.mathsystem.entity.graph.Color;

public class Vertex {
    private final String name;
    private final Color color;

    public Vertex(String name, Color color) {
        this.color = color;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("%s ", name, color);
    }
}
