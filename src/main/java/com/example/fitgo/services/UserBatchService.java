package com.example.fitgo.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fitgo.model.UserBatch;
import com.example.fitgo.model.Users;
import com.example.fitgo.repo.UserBatchRepo;

@Service
public class UserBatchService {
    @Autowired
    private UserBatchRepo repo;

    public UUID addBatch(Users user) {
        UserBatch userBatch = new UserBatch(user);
        userBatch = repo.save(userBatch);
        return userBatch.getBatchId();
    }
}
