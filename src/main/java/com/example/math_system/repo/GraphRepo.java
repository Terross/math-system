package com.example.math_system.repo;

import com.example.math_system.entity.graph.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphRepo extends JpaRepository<Graph, Long> {
}

