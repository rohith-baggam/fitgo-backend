package com.example.fitgo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.fitgo.controller.response.SuccessResponse;
import com.example.fitgo.model.Users;
import com.example.fitgo.repo.UserRepo;
import com.example.fitgo.services.UserBatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class BatchController {

    @Autowired
    private UserBatchService service;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/create-batch")
    public SuccessResponse createBatch() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users user = userRepo.findByUsername(userDetails.getUsername());
        Object message = new Object() {
            public String batchId = service.addBatch(user).toString();
        };
        return new SuccessResponse(message, 200);
    }

}
