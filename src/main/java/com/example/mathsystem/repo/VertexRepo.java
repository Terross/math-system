package com.example.mathsystem.repo;

import com.example.mathsystem.entity.graph.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VertexRepo extends JpaRepository<Vertex, Long> {
    public Vertex findVertexByName(String name);
}
