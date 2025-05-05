package com.example.fitgo.controller;

import java.rmi.server.UID;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.UserBatch;
import com.example.fitgo.repo.CoOrdinatesRepo;
import com.example.fitgo.repo.UserBatchRepo;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    @Autowired
    private CoOrdinatesRepo coOrdinatesRepo;

    @Autowired
    private UserBatchRepo userBatchRepo;

    private UUID userBatchId = UUID.fromString("ba4f0680-6b0d-44d5-a178-9e99de276835");

    @GetMapping("/greet")
    public double greet(HttpServletRequest request) {

        Optional<UserBatch> userBatch = userBatchRepo.findByBatchId(userBatchId);
        Optional<CoOrdinates> coOrdinate = coOrdinatesRepo.findTopByUserBatchIdOrderByCreatedDateDesc(userBatch.get());
        System.out.println("coOrdinate.get() : " + coOrdinate.get().getId());
        return coOrdinate.get().getId();
    }

    // @GetMapping("/greet")
    // public String greet() {
    // return "hello";
    // }
}
