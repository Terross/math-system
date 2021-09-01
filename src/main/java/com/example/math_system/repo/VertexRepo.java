package com.example.math_system.repo;

import com.example.math_system.entity.graph.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VertexRepo extends JpaRepository<Vertex, Long> {
}
