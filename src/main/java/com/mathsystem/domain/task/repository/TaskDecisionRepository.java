package com.mathsystem.domain.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskDecisionRepository extends JpaRepository<TaskDecision, UUID> {
}
