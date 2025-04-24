package com.example.fitgo.model;

import jakarta.persistence.*;

/**
 * The Users class represents a user entity in the application.
 * It is mapped to the "users" table in the database using JPA annotations.
 * The class contains fields such as id, username, and password for storing user
 * information.
 */
@Entity
@Table(name = "users")
public class Users {

    // The primary key for the Users entity. Automatically generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // The unique and non-null username of the user.
    @Column(nullable = false, unique = true)
    private String username;

    // The non-null password for the user.
    @Column(nullable = false)
    private String password;

    // Default constructor
    public Users() {
    }

    /**
     * Constructor to create a new user with the provided username and password.
     * 
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters

    /**
     * Gets the ID of the user.
     * 
     * @return The ID of the user.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the username of the user.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    // Setters

    /**
     * Sets the ID of the user. This is typically set by JPA.
     * 
     * @param id The ID to set for the user.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username The username to set for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password The password to set for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
