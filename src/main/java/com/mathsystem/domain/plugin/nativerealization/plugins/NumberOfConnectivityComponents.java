package com.mathsystem.domain.plugin.nativerealization.plugins;

import com.mathsystem.api.graph.model.Edge;
import com.mathsystem.api.graph.model.Graph;
import com.mathsystem.api.graph.model.Vertex;
import com.mathsystem.domain.plugin.plugintype.GraphCharacteristic;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class NumberOfConnectivityComponents implements GraphCharacteristic {

    private boolean[] marked;
    private int[] id;
    private int[] size;
    private int count;
    private List<List<Integer>> adj;


    @Override
    public Integer execute(Graph graph) {
        marked = new boolean[graph.getVertexCount()];
        id = new int[graph.getVertexCount()];
        size = new int[graph.getVertexCount()];
        count = 0;
        adj = new ArrayList<>();

        for (int i = 0; i < graph.getVertexCount(); i++) {
            adj.add(new ArrayList<>());
        }

        List<Vertex> vertices = new ArrayList<>();

        for (Map.Entry<UUID, Vertex> entry: graph.getVertices().entrySet()) {
            vertices.add(entry.getValue());
        }

        for (Edge edge : graph.getEdges()) {
            UUID fromV = edge.getFromV();
            UUID toV = edge.getToV();
            int v = vertices.indexOf(graph.getVertices().get(fromV));
            int w = vertices.indexOf(graph.getVertices().get(toV));

            adj.get(v).add(w);
            adj.get(w).add(v);
        }

        for (int v = 0; v < graph.getVertexCount(); v++) {
            if (!marked[v]) {
                dfs(graph, v);
                count++;
            }
        }

        return count;
    }


    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (int w : adj.get(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }
}
