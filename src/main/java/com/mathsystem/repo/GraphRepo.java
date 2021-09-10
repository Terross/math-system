package com.mathsystem.repo;

import com.mathsystem.entity.graph.Graph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraphRepo extends JpaRepository<Graph, Long> {
}

