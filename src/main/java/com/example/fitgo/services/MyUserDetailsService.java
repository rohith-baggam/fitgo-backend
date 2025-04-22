package com.example.fitgo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.fitgo.model.Users;
import com.example.fitgo.principal.UserPrincipal;
import com.example.fitgo.repo.UserRepo;

/**
 * MyUserDetailsService implements the UserDetailsService interface
 * to load user details by username from the database.
 * It uses the UserRepo to fetch user data and creates a UserPrincipal object
 * for authentication.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo; // Repository to fetch user data from the database

    /**
     * Loads the user details by username.
     * This method is used by Spring Security to authenticate the user.
     * 
     * @param username The username of the user to load.
     * @return UserDetails object containing user information.
     * @throws UsernameNotFoundException if the user with the given username is not
     *                                   found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the database by the provided username
        Users user = repo.findByUsername(username);

        // If the user is not found, throw an exception
        if (user == null) {
            System.out.println("User Not found"); // Log message for debugging purposes
            throw new UsernameNotFoundException("User Not found");
        }

        // Return a UserPrincipal object that implements UserDetails with the user data
        return new UserPrincipal(user);
    }

}
