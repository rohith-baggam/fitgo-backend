package com.example.fitgo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.fitgo.model.Users;
import com.example.fitgo.repo.UserRepo;

@Service
public class UserService {
    /**
     * User Service to Store Logics
     */
    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager authManager; // Ensure the correct naming here

    @Autowired
    private JWTService jwtService;

    public Users register(Users user) {

        return repo.save(user);
    }

    public String verify(Users user) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        }

        return "failure";
    }

    public boolean usernameExists(Users user) {
        return repo.findByUsername(user.getUsername()) != null;
    }
}
