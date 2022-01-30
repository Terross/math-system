package com.mathsystem.domain.task.graph.repo;

import com.mathsystem.domain.task.graph.repo.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VertexRepo extends JpaRepository<Vertex, Long> {
    public Vertex findVertexByName(String name);
}
