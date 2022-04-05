package com.mathsystem.api.graph.oldmodel.directed;

import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.domain.graph.repository.Color;


public class DirectedEdge extends AbstractEdge {


    public DirectedEdge(
            Vertex v,
            Vertex w,
            Integer weight,
            Color color,
            String label,
            String name
    ) {
        super(v, w, weight, color, label, name);
    }

    public Vertex from() {
        return v;
    }

    public Vertex to() {
        return w;
    }

    @Override
    public String toString() {
        return String.format("Edge %s = {" +
                        "\n%s -> %s" +
                        "\nweight =  %d" +
                        "\ncolor = %s" +
                        "\nlabel = %s",
                name, v.getName(), w.getName(), weight, color, label);
    }
}
