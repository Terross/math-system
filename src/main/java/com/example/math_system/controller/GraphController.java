package com.example.math_system.controller;

import com.example.math_system.entity.graph.Edge;
import com.example.math_system.entity.graph.Graph;
import com.example.math_system.entity.graph.Vertex;
import com.example.math_system.repo.EdgeRepo;
import com.example.math_system.repo.GraphRepo;
import com.example.math_system.repo.VertexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.rmi.NotBoundException;

@RestController
@RequestMapping("graph")
public class GraphController {

    private final GraphRepo graphRepo;
    private final VertexRepo vertexRepo;
    private final EdgeRepo edgeRepo;

    @Autowired
    public GraphController(GraphRepo graphRepo, VertexRepo vertexRepo, EdgeRepo edgeRepo) {
        this.graphRepo = graphRepo;
        this.vertexRepo = vertexRepo;
        this.edgeRepo = edgeRepo;
    }

    @GetMapping("api/getGraph/{id}")
    public Graph getGraph(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Graph graph = graphRepo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        System.out.println(graph);
        return graph;
    }

    @GetMapping("api/showGraph")
    public Graph getGraph() throws NotBoundException {
        return graphRepo.findAll().stream().findFirst().orElseThrow(NotBoundException::new);
    }

    @GetMapping("api/createGraph")
    public Graph startInit() throws FileNotFoundException {
        Graph graph = new Graph();
        graphRepo.save(graph);
        return graph;
    }

    @PostMapping("api/newVertex")
    public Graph addNewVertex(@RequestBody Vertex vertex) {
        Graph graph = graphRepo.findAll().get(0);
        graph.addVertex(vertex);
        graphRepo.save(graph);
        return graph;
    }

    @PostMapping("api/newEdge")
    public Graph addNewEdge(@RequestBody Edge edge) {
        Graph graph = graphRepo.findAll().get(0);
//        Vertex from = vertexRepo.findVertexByName(edge.getFrom());
//        Vertex to = vertexRepo.findVertexByName(edge.getTo());
//        graph.addEdge(edge, from, to);
//        graphRepo.save(graph);
        return graph;
    }

    @PutMapping("api/updateVertex/{id}")
    public Vertex updateVertex(@PathVariable("id") Long vertexId,
                               @RequestBody Vertex vertex) {
        Vertex vertexFromDB = vertexRepo.getById(vertexId);

        String newColor = vertex.getColor();
        String newName = vertex.getName();

        if (newColor != null) {
            vertexFromDB.setColor(newColor);
        }
        if (newName != null) {
            vertexFromDB.setName(newName);
        }

        vertexRepo.save(vertexFromDB);

        return vertexFromDB;
    }

    @PutMapping("api/updateEdge/{id}")
    public Edge updateEdge(@PathVariable("id") Long edgeId,
                           @RequestBody Edge edge) {
        Edge edgeFromDB = edgeRepo.getById(edgeId);

//        String newFrom = edge.getFrom();
//        String newTo = edge.getTo();
//        Double newWeight = edge.getWeight();
//
//        if (newFrom != null) {
//            edgeFromDB.setFrom(newFrom);
//        }
//
//        if (newTo != null) {
//            edgeFromDB.setTo(newTo);
//        }
//
//        if (newWeight != null ) {
//            edgeFromDB.setWeight(newWeight);
//        }

        edgeRepo.save(edgeFromDB);

        return edgeFromDB;
    }

    @DeleteMapping("api/deleteVertex/{id}")
    public void deleteVertex(@PathVariable("id") Long vertexId) {

        Vertex vertexFromDB = vertexRepo.findById(vertexId).orElseThrow(IndexOutOfBoundsException::new);
        vertexRepo.delete(vertexFromDB);
    }

    @DeleteMapping("api/deleteEdge/{id}")
    public void deleteEdge(@PathVariable("id") Long edgeId) {
        System.out.println(123);
        System.out.println(edgeId);
        System.out.println(edgeRepo.findAll().stream().findFirst());
        Edge edgeFromDB = edgeRepo.findById(edgeId).orElseThrow(IndexOutOfBoundsException::new);
        edgeRepo.delete(edgeFromDB);
    }
}
