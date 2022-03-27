package com.mathsystem.domain.graph.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GraphRepository extends JpaRepository<GraphProjection, UUID> {
}

