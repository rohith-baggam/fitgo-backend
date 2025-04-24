package com.example.fitgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fitgo.controller.response.SuccessResponse;
import com.example.fitgo.controller.validations.APIExceptions;
import com.example.fitgo.controller.validations.UsernameAlreadyExistsException;
import com.example.fitgo.model.Users;
import com.example.fitgo.services.UserService;

/**
 * AuthController class handles authentication-related requests like login and
 * registration.
 * It processes login requests by verifying credentials and registration
 * requests by creating new users
 * while checking for username uniqueness and encrypting passwords.
 */
@RestController
public class AuthController {

    /**
     * Service class that contains the business logic for user operations like
     * registration and authentication.
     */
    @Autowired
    private UserService service;

    /**
     * Endpoint for user login. This endpoint checks if the username exists in the
     * system and then verifies
     * the provided credentials. If successful, it returns a JWT token.
     * 
     * @param user The user object containing the login credentials (username and
     *             password).
     * @return A SuccessResponse containing the JWT token if authentication is
     *         successful.
     * @throws UsernameAlreadyExistsException if the username does not exist in the
     *                                        system.
     * @throws APIExceptions                  if any error occurs during the
     *                                        process.
     */
    @PostMapping("/login")
    public SuccessResponse login(@RequestBody Users user) {
        // Check if the username exists in the system
        if (!service.usernameExists(user)) {
            throw new UsernameAlreadyExistsException("Username not exists");
        }
        try {
            // Print the username for debugging (can be removed in production)
            System.out.println(user.getUsername());

            // Generate JWT token after successful verification
            Object message = new Object() {
                public String token = service.verify(user);
            };

            // Return the success response with the generated token
            return new SuccessResponse(message, 200);
        } catch (Exception e) {
            // Handle any exceptions and wrap them in a custom APIExceptions class
            throw new APIExceptions(e.getMessage());
        }
    }

    /**
     * Password encoder for encoding the user's password before saving it to the
     * database.
     */
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Endpoint for user registration. This endpoint checks if the username already
     * exists in the system,
     * and if it doesn't, it hashes the password and saves the new user to the
     * system.
     * 
     * @param user The user object containing the registration details (username and
     *             password).
     * @return The saved user object with the hashed password.
     * @throws UsernameAlreadyExistsException if the username already exists in the
     *                                        system.
     * @throws APIExceptions                  if any error occurs during the
     *                                        process.
     */
    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        // Check if the username already exists in the system
        if (service.usernameExists(user)) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        try {
            // Hash the user's password before saving it
            user.setPassword(encoder.encode(user.getPassword()));

            // Register the user and return the saved user object
            return service.register(user);
        } catch (Exception e) {
            // Handle any exceptions and wrap them in a custom APIExceptions class
            throw new APIExceptions(e.getMessage());
        }
    }
}
