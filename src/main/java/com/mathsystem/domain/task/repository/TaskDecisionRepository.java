package com.mathsystem.domain.task.repository;

import com.mathsystem.domain.user.repository.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskDecisionRepository extends JpaRepository<TaskDecision, UUID> {
    List<TaskDecision> findAllByUser(User user);
}
