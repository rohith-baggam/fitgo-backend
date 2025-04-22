package com.example.fitgo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.fitgo.model.Users;
import com.example.fitgo.repo.UserRepo;

/**
 * UserService class contains the business logic related to user operations,
 * such as registration, authentication, and verification.
 * It uses the UserRepository for data persistence and AuthenticationManager
 * to handle authentication logic.
 */
@Service
public class UserService {

    /**
     * Repository for interacting with the users table in the database.
     */
    @Autowired
    private UserRepo repo;

    /**
     * AuthenticationManager to authenticate the user during login.
     */
    @Autowired
    private AuthenticationManager authManager;

    /**
     * JWTService to generate JWT tokens after successful authentication.
     */
    @Autowired
    private JWTService jwtService;

    /**
     * Registers a new user in the system by saving the user to the repository.
     * 
     * @param user The user object containing the details to be saved.
     * @return The saved user object.
     */
    public Users register(Users user) {
        return repo.save(user);
    }

    /**
     * Verifies the user's credentials and generates a JWT token if authentication
     * is successful.
     * 
     * @param user The user object containing the login credentials (username and
     *             password).
     * @return A JWT token if authentication is successful, or "failure" if
     *         authentication fails.
     */
    public String verify(Users user) {
        // Attempt authentication using the provided username and password
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword()));

        // If authentication is successful, generate a JWT token
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        }

        // Return failure if authentication is unsuccessful
        return "failure";
    }

    /**
     * Checks if a user with the given username already exists in the system.
     * 
     * @param user The user object containing the username to check.
     * @return true if a user with the given username exists, false otherwise.
     */
    public boolean usernameExists(Users user) {
        return repo.findByUsername(user.getUsername()) != null;
    }
}
