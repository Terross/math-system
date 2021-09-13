package com.mathsystem.graphapi;

import com.mathsystem.entity.graph.Color;

public class Vertex {
    private final String name;
    private final Color color;

    public Vertex(Color color, String name) {
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
        return String.format("%s (%s)", name, color);
    }

}
