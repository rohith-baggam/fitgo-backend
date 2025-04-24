package com.example.fitgo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

/**
 * Entity class representing geographic coordinates associated with a user
 * batch.
 */
@Entity
@Table(name = "CO_ORDINATES_TABLE")
public class CoOrdinates {

    /**
     * Primary key for the coordinate entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Latitude value of the location.
     */
    @Column(name = "LATITUDE")
    private double latitude;

    /**
     * Longitude value of the location.
     */
    @Column(name = "LONGITUDE")
    private double longitude;

    /**
     * Reference to the UserBatch that this coordinate is associated with.
     */
    @ManyToOne
    @JoinColumn(name = "USER_BATCH_ID")
    private UserBatch userBatchId;

    /**
     * Timestamp indicating when the coordinate entry was created.
     */
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;

    /**
     * Timestamp indicating the last time the coordinate entry was updated.
     */
    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    /**
     * Automatically sets the created date before persisting.
     */
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    /**
     * Automatically updates the updated date before any update operation.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    // constructor
    public CoOrdinates() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public CoOrdinates(double latitude, double longitude, UserBatch userBatchId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userBatchId = userBatchId;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public UserBatch getUserBatchId() {
        return userBatchId;
    }

    public void setUserBatchId(UserBatch userBatchId) {
        this.userBatchId = userBatchId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
