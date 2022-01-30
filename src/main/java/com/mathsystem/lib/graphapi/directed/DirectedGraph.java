package com.mathsystem.graphapi.directed;

import com.mathsystem.domain.task.graph.repo.Color;
import com.mathsystem.domain.task.graph.repo.Edge;
import com.mathsystem.graphapi.AbstractGraph;
import com.mathsystem.graphapi.Vertex;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Класс ориентированного графа
 */
@Data
public class DirectedGraph extends AbstractGraph {

    /**
     * Конструктор - создание нового объекта
     * ориентированного графа с определенными значениями.
     * Используется в контроллерах - не используется в плагинах
     * @param vertexCount - количество вершин
     * @param edgeCount - количество ребер
     * @param edges - список ребер
     * @param vertices - список вершин
     */
    public DirectedGraph(int vertexCount,
                         int edgeCount,
                         List<Edge> edges,
                         List<com.mathsystem.domain.task.graph.repo.Vertex> vertices) {
        this.vertexCount = vertexCount;
        this.edgeCount = edgeCount;
        this.vertices = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            com.mathsystem.domain.task.graph.repo.Vertex v =  vertices.get(i);
            this.vertices.add(new Vertex(
                    Integer.parseInt(v.getName()),
                    v.getName(),
                    v.getColor(),
                    v.getWeight(),
                    v.getLabel(),
                    new LinkedList<>()
            ));
        }

        for (Edge edge: edges) {
            this.vertices
                    .get(Integer.parseInt(edge.getFromVertex().getName()))
                    .getEdgeList().add(new DirectedEdge(
                            this.vertices.get(Integer.parseInt(edge.getFromV())),
                            this.vertices.get(Integer.parseInt(edge.getToV())),
                            edge.getWeight() == null ? 0 : edge.getWeight(),
                            edge.getColor(),
                            edge.getLabel(),
                            edge.getName()
                    ));
        }
    }

    /**
     * Конструктор - создание нового объекта
     * ориентированного графа с определенными значениями из файла.
     * Используется в плагинах
     * @param file - объект файла с графом
     * @throws FileNotFoundException
     */
    public DirectedGraph(File file)  {
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

            this.vertices.get(Integer.parseInt(v.getName()))
                    .getEdgeList().add(new DirectedEdge(
                            v,
                            w,
                            scanner.nextInt(),
                            Color.valueOf(scanner.next().toLowerCase()),
                            scanner.next(),
                            scanner.next()
                    ));
        }
    }

    @Override
    public String toString() {
        return "DirectedGraph = {" + super.toString()
                + "\n}";
    }
}
