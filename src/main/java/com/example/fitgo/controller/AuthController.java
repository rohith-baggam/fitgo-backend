package com.example.fitgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fitgo.controller.validations.APIExceptions;
import com.example.fitgo.controller.validations.UsernameAlreadyExistsException;
import com.example.fitgo.model.Users;
import com.example.fitgo.services.UserService;

@RestController
public class AuthController {
    @Autowired
    private UserService service;

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        if (!service.usernameExists(user)) {
            throw new UsernameAlreadyExistsException("Username not exists");
        }
        try {
            System.out.println(user.getUsername());
            return service.verify(user);
        } catch (Exception e) {
            throw new APIExceptions(e.getMessage());
        }
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        if (service.usernameExists(user)) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            return service.register(user);
        } catch (Exception e) {
            throw new APIExceptions(e.getMessage());
        }

    }
}
