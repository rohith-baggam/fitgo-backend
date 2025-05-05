package com.example.fitgo.controller;

import com.example.fitgo.controller.response.SuccessResponse;
import com.example.fitgo.controller.validations.APIExceptions;
import com.example.fitgo.dto.CoOrdinatePayloadDTO;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.UserBatch;
import com.example.fitgo.model.Users;
import com.example.fitgo.repo.CoOrdinatesRepo;
import com.example.fitgo.repo.UserRepo;
import com.example.fitgo.services.CoOrdinatesService;
import com.example.fitgo.services.RoundAnalyticsService;
import com.example.fitgo.services.UserBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoadCoOrdinateController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoundAnalyticsService roundAnalyticsService;

    @Autowired
    private UserBatchService userBatchService;

    @Autowired
    private CoOrdinatesRepo coOrdinatesRepo;

    @Transactional
    @PostMapping("/add-co-ordinates")
    public SuccessResponse addCoOrdinates(@RequestBody CoOrdinatePayloadDTO loadCoOrdinateDTO) {
        try {
            System.out.println(1);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(2);
            Users loggedInUser = userRepo.findByUsername(userDetails.getUsername());
            System.out.println(3 + " " + loadCoOrdinateDTO.getBatchId());
            UserBatch userBatch = userBatchService.getUserBatchById(loadCoOrdinateDTO.getBatchId());
            System.out.println(4);
            if (userBatch == null) {
                throw new APIExceptions("Invalid batch ID");
            }
            System.out.println(5);
            Users batchUser = userBatch.getUser();

            if (batchUser == null || !batchUser.getId().equals(loggedInUser.getId())) {
                throw new APIExceptions("Batch ID is not assigned to the logged-in user");
            }
            System.out.println(6);

            if (coOrdinatesRepo.existsByUserBatchIdAndLatitudeAndLongitude(
                    userBatch,
                    loadCoOrdinateDTO.getLatitude(),
                    loadCoOrdinateDTO.getLongitude())) {
                throw new APIExceptions("Coordinates already exist");
            }
            System.out.println(7);

            CoOrdinates saved = roundAnalyticsService.handleRoundLogic(loadCoOrdinateDTO);
            System.out.println(8);
            if (saved == null) {
                throw new APIExceptions("Coordinates already exist");
            }
            System.out.println(9);
            Object message = new Object() {
                public Long id = saved.getId();
                public String status = "Coordinate added successfully";
            };
            System.out.println(10);
            return new SuccessResponse(message, 200);
        } catch (Exception e) {
            throw new APIExceptions(e.getMessage());
        }

    }

}

// @PostMapping("/add-co-ordinates")
// public CoOrdinates addCoOrdinates(@RequestBody LoadCoOrdinateDTO
// loadCoOrdinateDTO) {
// System.out.println(1);
// Users loggedInUser = getAuthenticatedUser();
// System.out.println(2);
// UserBatch userBatch =
// userBatchService.getUserBatchById(loadCoOrdinateDTO.getBatchId());
// System.out.println(3);
// if (userBatch == null) {
// throw new APIExceptions("Invalid batch ID");
// }
// System.out.println(4);
// Users batchUser = userBatch.getUser();
// System.out.println(5);
// if (batchUser == null || !batchUser.getId().equals(loggedInUser.getId())) {
// throw new APIExceptions("Batch ID is not assigned to the logged-in user");
// }
// System.out.println(6);
// CoOrdinates saved = coOrdinatesService.saveIfNotDuplicate(
// loadCoOrdinateDTO.getLatitude(),
// loadCoOrdinateDTO.getLongitude(),
// userBatch);
// System.out.println(7);
// if (saved == null) {
// throw new APIExceptions("Coordinates already exist");
// }
// System.out.println(8);
// return saved;
// }

// private Users getAuthenticatedUser() {
// Authentication authentication =
// SecurityContextHolder.getContext().getAuthentication();
// UserDetails userDetails = (UserDetails) authentication.getPrincipal();
// return userRepo.findByUsername(userDetails.getUsername());
// }