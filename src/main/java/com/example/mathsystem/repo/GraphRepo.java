package com.example.mathsystem.repo;

import com.example.mathsystem.entity.graph.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphRepo extends JpaRepository<Graph, Long> {
}

