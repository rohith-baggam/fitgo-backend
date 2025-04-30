package com.example.fitgo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.RoundIdTable;
import com.example.fitgo.model.UserBatch;

public interface CoOrdinatesRepo extends JpaRepository<CoOrdinates, Integer> {
    Optional<CoOrdinates> findTopByUserBatchIdOrderByCreatedDateDesc(UserBatch userBatchId);

    Optional<CoOrdinates> findTopByRoundOrderByCreatedDateDesc(RoundIdTable round);

    Optional<CoOrdinates> findTopByRoundOrderByCreatedDateAsc(RoundIdTable round);

    List<CoOrdinates> findByRound(RoundIdTable roundIdTable);

    // Corrected method for fetching by RoundIdTable's Id
    List<CoOrdinates> findByRound_Id(Long roundId);

    List<CoOrdinates> findByUserBatchId(UserBatch userBatch);

    List<CoOrdinates> findByUserBatchId_Id(Long userBatchId);

    boolean existsByUserBatchId(UserBatch userBatchId);

    boolean existsByRound(RoundIdTable round);

    // Corrected method for fetching by RoundIdTable's Id

    // Optional<CoOrdinates> findTopByUserBatchIdOrderByCreatedDateDesc(UserBatch
    // userBatchId);

    // List<CoOrdinates> findByRound(RoundIdTable roundIdTable);

    // List<CoOrdinates> findByRound_Id(Long roundId);

    // List<CoOrdinates> findByUserBatchId(UserBatch userBatch);

    // List<CoOrdinates> findByUserBatchId_Id(Long roundIdTableId);

    // boolean existsByUserBatchId(UserBatch userBatchId);

    // // boolean existsByRoundIdTable(RoundIdTable roundIdTable);

    // boolean existsByRound(RoundIdTable round);

    // List<CoOrdinates> findByRoundIdTable_Id(RoundIdTable roundIdTable);

    // // CoOrdinates getLastCoOrdinateOrNull(UserBatch userBatch);
}
