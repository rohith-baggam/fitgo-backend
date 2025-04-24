package com.example.fitgo.controller.dashboard;

import org.springframework.web.bind.annotation.RestController;

import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.repo.CoOrdinatesRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CoOrdinatesController {
    @Autowired
    CoOrdinatesRepo repo;

    @GetMapping("/co-ordinates-batch-api/")
    public List<CoOrdinates> getCoordinatesByBatch(@RequestParam("batchId") Long batchId) {
        return repo.findByUserBatchId_Id(batchId);
    }
}
