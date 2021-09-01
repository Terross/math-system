package com.example.math_system.repo;

import com.example.math_system.entity.task.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
}
