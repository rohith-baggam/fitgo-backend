package com.example.fitgo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fitgo.controller.validations.APIExceptions;
import com.example.fitgo.dto.LoadCoOrdinateDTO;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.UserBatch;
import com.example.fitgo.model.Users;
import com.example.fitgo.repo.UserRepo;
import com.example.fitgo.services.CoOrdinatesService;
import com.example.fitgo.services.UserBatchService;

@RestController
public class LoadCoOrdinateController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CoOrdinatesService service;

    @Autowired
    private UserBatchService userBatchService;

    @PostMapping("/add-co-ordinates")
    public CoOrdinates addCoOrdinates(@RequestBody LoadCoOrdinateDTO loadCoOrdinateDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Users user = userRepo.findByUsername(userDetails.getUsername());
            UserBatch userBatch = userBatchService.getUserBatchById(loadCoOrdinateDTO.getBatchId());
            String validationMessage = "";
            if (userBatch == null) {
                validationMessage = "Incorrect batch Id";
                throw new APIExceptions(validationMessage);
            }

            if (userBatch.getUser() == null) {
                validationMessage = "Invalid batch user";
                throw new APIExceptions(validationMessage);
            }

            if (userBatch.getUser().getId() != user.getId()) {
                validationMessage = "Batch ID is not assigned to login user";
                throw new APIExceptions(validationMessage);
            }

            if (validationMessage != "") {
                throw new APIExceptions(validationMessage);
            }

            CoOrdinates newCOrdinates = service.saveIfNotDuplicate(loadCoOrdinateDTO.getLatitude(),
                    loadCoOrdinateDTO.getLongitude(), userBatch);

            if (newCOrdinates != null) {
                return newCOrdinates;
            } else {
                throw new APIExceptions("Co-ordiantes already exists");
            }

        } catch (Exception e) {
            throw new APIExceptions(e.getMessage());
        }
    }
}
