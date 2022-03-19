package com.mathsystem.lib.graphapi.undirected;

import com.mathsystem.domain.graph.repository.Color;
import com.mathsystem.domain.graph.repository.EdgeProjection;
import com.mathsystem.domain.graph.repository.VertexProjection;
import com.mathsystem.lib.graphapi.AbstractGraph;
import com.mathsystem.lib.graphapi.Vertex;
import lombok.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Класс неориентированного графа
 */
@Data
public class UndirectedGraph extends AbstractGraph {

    /**
     * Конструктор - создание нового объекта
     * неориентированного графа с определенными значениями.
     * Используется в контроллерах - не используется в плагинах
     * @param vertexCount - количество вершин
     * @param edgeCount - количество ребер
     * @param edgeProjections - список ребер
     * @param vertices - список вершин
     */
    public UndirectedGraph(int vertexCount,
                           int edgeCount,
                           List<EdgeProjection> edgeProjections,
                           List<VertexProjection> vertices) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.vertices = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            VertexProjection v =  vertices.get(i);
            this.vertices.add(new Vertex(
                    Integer.parseInt(v.getName()),
                    v.getName(),
                    v.getColor(),
                    v.getWeight(),
                    v.getLabel(),
                    new LinkedList<>()
            ));
        }

        for (EdgeProjection edgeProjection : edgeProjections) {
            UndirectedEdge undirectedEdge = new UndirectedEdge(
                    this.vertices.get(Integer.parseInt(edgeProjection.getFromV())),
                    this.vertices.get(Integer.parseInt(edgeProjection.getToV())),
                    edgeProjection.getWeight() == null ? 0 : edgeProjection.getWeight(),
                    edgeProjection.getColor(),
                    edgeProjection.getLabel()
            );

            this.vertices
                    .get(Integer.parseInt(edgeProjection.getFromVertexProjection().getName()))
                    .getEdgeList().add(undirectedEdge);
            this.vertices
                    .get(Integer.parseInt(edgeProjection.getToVertexProjection().getName()))
                    .getEdgeList().add(undirectedEdge);
        }
    }

    /**
     * Конструктор - создание нового объекта
     * неориентированного графа с определенными значениями из файла.
     * Используется в плагинах
     * @param file - объект файла с графом
     * @throws FileNotFoundException
     */
    public UndirectedGraph(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        }

        this.vertexCount = scanner.nextInt();
        this.edgeCount = scanner.nextInt();
        this.vertices = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            String name = scanner.next();
            this.vertices.add(new Vertex(
                    Integer.parseInt(name),
                    name,
                    Color.valueOf(scanner.next().toLowerCase()),
                    scanner.nextInt(),
                    scanner.next(),
                    new LinkedList<>()
            ));
        }

        for (int i = 0; i < edgeCount; i++) {
            Vertex v = this.vertices.get(Integer.parseInt(scanner.next()));
            Vertex w = this.vertices.get(Integer.parseInt(scanner.next()));

            UndirectedEdge undirectedEdge = new UndirectedEdge(
                    v,
                    w,
                    scanner.nextInt(),
                    Color.valueOf(scanner.next().toLowerCase()),
                    scanner.next()
            );

            this.vertices.get(Integer.parseInt(v.getName()))
                    .getEdgeList().add(undirectedEdge);
            this.vertices.get(Integer.parseInt(w.getName()))
                    .getEdgeList().add(undirectedEdge);
        }
    }

    @Override
    public String toString() {
        return "UndirectedGraph = {" + super.toString()
                + "\n}";
    }
}
