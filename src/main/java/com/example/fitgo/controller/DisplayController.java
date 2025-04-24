package com.example.fitgo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisplayController {
    @GetMapping
    public String displayTracker(@RequestParam("batchId") Long batchId) {
        return null;
    }
}
