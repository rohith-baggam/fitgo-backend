package com.example.fitgo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.UserBatch;

public interface CoOrdinatesRepo extends JpaRepository<CoOrdinates, Integer> {
    Optional<CoOrdinates> findTopByUserBatchIdOrderByCreatedDateDesc(UserBatch userBatchId);

    List<CoOrdinates> findByUserBatchId_Id(Long userBatchId);
}
