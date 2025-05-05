package com.example.fitgo.controller.dashboard;

import org.springframework.web.bind.annotation.RestController;

import com.example.fitgo.dto.CoOrdinateProjectionDTO;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.repo.CoOrdinatesRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CoOrdinatesController {
    @Autowired
    CoOrdinatesRepo repo;

    @GetMapping("/co-ordinates-batch-api/")
    public List<CoOrdinateProjectionDTO> getCoordinatesByBatch(@RequestParam("batchId") Long batchId) {

        List<CoOrdinateProjectionDTO> coOrdinateProjectionDTOs = repo.findByUserBatchId_Id(
                batchId,
                CoOrdinateProjectionDTO.class);
        return coOrdinateProjectionDTOs;
    }

    @GetMapping("/get-latest-co-ordinate-api")
    public CoOrdinateProjectionDTO getLatestCoOrdinate(@RequestParam("batchId") Long batchId) {
        Optional<CoOrdinateProjectionDTO> latest = repo
                .findTopByUserBatchId_IdOrderByCreatedDateDesc(batchId, CoOrdinateProjectionDTO.class);

        return latest.get();
    }
    // @GetMapping("/co-ordinates-batch-api/")
    // public Long getCoordinatesByBatch(@RequestParam("batchId") Long batchId) {
    // return batchId;
    // }
}
