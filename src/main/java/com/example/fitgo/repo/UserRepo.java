package com.example.fitgo.repo;

import org.springframework.stereotype.Repository;

import com.example.fitgo.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
