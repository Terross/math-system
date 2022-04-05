package com.mathsystem.api.graph.oldmodel.undirected;

import com.mathsystem.api.graph.oldmodel.AbstractEdge;
import com.mathsystem.api.graph.oldmodel.Vertex;
import com.mathsystem.domain.graph.repository.Color;


public class UndirectedEdge extends AbstractEdge {

    public UndirectedEdge(
            Vertex v,
            Vertex w,
            Integer weight,
            Color color,
            String label,
            String name
    ) {
        super(v, w, weight, color, label, name);
    }

    @Override
    public String toString() {
        return String.format("Edge %s = {" +
                        "\n%s <-> %s" +
                        "\nweight =  %d" +
                        "\ncolor = %s" +
                        "\nlabel = %s",
                name, v.getName(), w.getName(), weight, color, label);
    }
}
