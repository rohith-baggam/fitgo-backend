package com.example.fitgo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

import org.hibernate.annotations.UuidGenerator;

/**
 * The UserBatch class represents a batch associated with a user.
 * It is mapped to the "USER_BATCH_TABLE" table in the database using JPA
 * annotations.
 * The class contains fields for the batch ID, user association, creation and
 * update timestamps.
 */
@Entity
@Table(name = "USER_BATCH_TABLE")
public class UserBatch {

    // The primary key for the UserBatch entity. Automatically generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_BATCH_ID")
    private Integer id;

    // The unique batch ID for the user batch. Automatically generated as a UUID.
    @Column(name = "BATCH_ID", nullable = false, unique = true, updatable = false)
    @UuidGenerator
    private UUID batchId;

    // The user associated with this batch.
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private Users user;

    // This is the First CoOrdinate of the joureny
    @ManyToOne
    @JoinColumn(name = "FIRST_COORDINATE")
    private CoOrdinates firstCoOrdinate;

    // The timestamp when the batch was created. It is set before persisting.
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;

    // The timestamp when the batch was last updated.
    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    /**
     * Constructor to create a UserBatch with the associated user.
     * 
     * @param user The user to associate with this batch.
     */
    public UserBatch(Users user) {
        this.user = user;
    }

    public UserBatch() {
    }

    public UserBatch(UUID batchId) {
        this.batchId = batchId;
    }

    /**
     * This method is invoked before the entity is persisted to the database.
     * It sets the createdDate to the current timestamp.
     */
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    /**
     * This method is invoked before the entity is updated in the database.
     * It sets the updatedDate to the current timestamp.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    // Getters and Setters

    /**
     * Gets the ID of the UserBatch.
     * 
     * @return The ID of the UserBatch.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the UserBatch.
     * 
     * @param id The ID to set for the UserBatch.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the UUID of the batch.
     * 
     * @return The UUID of the batch.
     */
    public UUID getBatchId() {
        return batchId;
    }

    /**
     * Sets the UUID of the batch.
     * 
     * @param batchId The UUID to set for the batch.
     */
    public void setBatchId(UUID batchId) {
        this.batchId = batchId;
    }

    /**
     * Gets the user associated with this batch.
     * 
     * @return The user associated with this batch.
     */
    public Users getUser() {
        return user;
    }

    /**
     * Sets the user associated with this batch.
     * 
     * @param user The user to set for this batch.
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * Gets the created timestamp of the batch.
     * 
     * @return The created timestamp.
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created timestamp of the batch.
     * 
     * @param createdDate The created timestamp to set for the batch.
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the updated timestamp of the batch.
     * 
     * @return The updated timestamp.
     */
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Sets the updated timestamp of the batch.
     * 
     * @param updatedDate The updated timestamp to set for the batch.
     */
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setFirstCoOrdinate(CoOrdinates firstCoOrdinate) {
        this.firstCoOrdinate = firstCoOrdinate;
    }

    public CoOrdinates getFirstCoOrdinate() {
        return this.firstCoOrdinate;
    }
}
