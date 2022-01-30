package com.mathsystem.domain.task.graph.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VertexRepository extends JpaRepository<VertexProjection, UUID> {
    public VertexProjection findVertexByName(String name);
}