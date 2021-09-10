package com.mathsystem.repo;

import com.mathsystem.entity.graph.Edge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EdgeRepo extends JpaRepository<Edge, Long> {
}
