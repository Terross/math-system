package com.mathsystem.domain.plugin.repository;

import com.mathsystem.api.graph.model.GraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PluginRepository extends JpaRepository<PluginProjection, UUID> {
    List<PluginProjection> findAlgorithmByName(String name);
    List<PluginProjection> findAlgorithmByFileName(String fileName);
    List<PluginProjection> findAllByGraphType(GraphType graphType);
}
