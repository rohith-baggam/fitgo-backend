package com.example.fitgo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.fitgo.controller.validations.APIExceptions;
import com.example.fitgo.model.UserBatch;
import com.example.fitgo.services.UserBatchService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AppDisplayController {
    @Autowired
    private UserBatchService userBatchService;

    @GetMapping("/home")
    public String appDisplayController(@RequestParam UUID batchId) {
        if (batchId == null) {
            throw new APIExceptions("Batch Id required");
        }
        UserBatch userBatch = userBatchService.getUserBatchById(batchId);
        if (userBatch == null) {
            throw new APIExceptions("Incorrect Batch ID");
        }

        return "Hello " + userBatch.getUser().getUsername() + " Start FitGO";
    }

}
