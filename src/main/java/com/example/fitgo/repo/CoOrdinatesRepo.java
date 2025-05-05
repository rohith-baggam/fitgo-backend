package com.example.fitgo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.fitgo.dto.CoOrdinateProjectionDTO;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.RoundIdTable;
import com.example.fitgo.model.UserBatch;

public interface CoOrdinatesRepo extends JpaRepository<CoOrdinates, Integer> {

    Optional<CoOrdinates> findTopByUserBatchIdOrderByCreatedDateDesc(UserBatch userBatchId);

    Optional<CoOrdinates> findTopByUserBatchIdOrderByCreatedDateAsc(UserBatch userBatchId); // ✅ ADD THIS

    Optional<CoOrdinates> findTopByRoundOrderByCreatedDateDesc(RoundIdTable round);

    Optional<CoOrdinates> findTopByRoundOrderByCreatedDateAsc(RoundIdTable round);

    List<CoOrdinates> findByRound(RoundIdTable roundIdTable);

    List<CoOrdinates> findByRound_Id(Long roundId);

    List<CoOrdinates> findByUserBatchId(UserBatch userBatch);

    // ✅ Full entity
    List<CoOrdinates> findByUserBatchId_Id(Long userBatchId);

    // ✅ Projection
    List<CoOrdinateProjectionDTO> findByUserBatchId_Id(Long userBatchId, Class<CoOrdinateProjectionDTO> type);

    <T> Optional<T> findTopByUserBatchId_IdOrderByCreatedDateDesc(Long batchId, Class<T> type);

    boolean existsByUserBatchId(UserBatch userBatchId);

    boolean existsByRound(RoundIdTable round);

    boolean existsByUserBatchIdAndLatitudeAndLongitude(UserBatch userBatchId, double latitude, double longitude);

}
