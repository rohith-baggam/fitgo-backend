package com.example.fitgo.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fitgo.model.UserBatch;

public interface UserBatchRepo extends JpaRepository<UserBatch, Integer> {
    Optional<UserBatch> findByBatchId(UUID batchId);
}
