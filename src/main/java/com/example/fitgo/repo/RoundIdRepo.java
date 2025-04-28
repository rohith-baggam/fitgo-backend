package com.example.fitgo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fitgo.model.RoundIdTable;

public interface RoundIdRepo extends JpaRepository<RoundIdTable, Integer> {

}
