package com.mathsystem.domain.plugin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PluginAnswerRepository extends JpaRepository<PluginAnswerProjection, UUID> {
}
