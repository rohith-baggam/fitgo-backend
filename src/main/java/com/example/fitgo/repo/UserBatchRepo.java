package com.example.fitgo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fitgo.model.UserBatch;

public interface UserBatchRepo extends JpaRepository<UserBatch, Integer> {

}
