package com.example.fitgo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CO_ORDINATES_TABLE")
public class CoOrdinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    @ManyToOne
    @JoinColumn(name = "USER_BATCH_ID")
    private UserBatch userBatchId;

    @ManyToOne
    @JoinColumn(name = "LAST_COORDINATE")
    private CoOrdinates lastCoOrdinate;

    @ManyToOne
    @JoinColumn(name = "ROUND")
    private RoundIdTable round;

    @Column(name = "DISTANCE")
    private Double distance;

    @Column(name = "TIME")
    private Double time;

    @Column(name = "SPEED")
    private Double speed;

    @Column(name = "TOTAL_DISTANCE")
    private Double totalDistance;

    @Column(name = "TOTAL_TIME")
    private Double totalTime;

    @Column(name = "TOTAL_SPEED")
    private Double totalSpeed;

    @Column(name = "ROUND_DISTANCE")
    private Double roundDistance;

    @Column(name = "ROUND_TIME")
    private Double roundTime;

    @Column(name = "ROUND_SPEED")
    private Double roundSpeed;

    @Column(name = "IS_START_POINT")
    private Boolean isStartPoint = false;

    @Column(name = "IS_END_POINT")
    private Boolean isEndPoint = false;

    @Column(name = "IS_ROUND_START_POINT")
    private Boolean isRoundStartPoint = false;

    @Column(name = "IS_ROUND_END_POINT")
    private Boolean isRoundEndPoint = false;

    @Column(name = "EXPECTED_TIME")
    private Double expectedTime;

    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    // constructors
    public CoOrdinates(double latitude, double longitude, UserBatch userBatch) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userBatchId = userBatch;
    }

    public CoOrdinates(double latitude, double longitude, UserBatch userBatch, boolean isStartPoint,
            boolean isRoundStartPoint) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userBatchId = userBatch;
        this.isStartPoint = isStartPoint;
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

    public CoOrdinates getLastCoOrdinate() {
        return lastCoOrdinate;
    }

    public void setLastCoOrdinate(CoOrdinates lastCoOrdinate) {
        this.lastCoOrdinate = lastCoOrdinate;
    }

    public RoundIdTable getRound() {
        return round;
    }

    public void setRound(RoundIdTable round) {
        this.round = round;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    public Double getTotalSpeed() {
        return totalSpeed;
    }

    public void setTotalSpeed(Double totalSpeed) {
        this.totalSpeed = totalSpeed;
    }

    public Double getRoundDistance() {
        return roundDistance;
    }

    public void setRoundDistance(Double roundDistance) {
        this.roundDistance = roundDistance;
    }

    public Double getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(Double roundTime) {
        this.roundTime = roundTime;
    }

    public Double getRoundSpeed() {
        return roundSpeed;
    }

    public void setRoundSpeed(Double roundSpeed) {
        this.roundSpeed = roundSpeed;
    }

    public Boolean getIsStartPoint() {
        return isStartPoint;
    }

    public void setIsStartPoint(Boolean isStartPoint) {
        this.isStartPoint = isStartPoint;
    }

    public Boolean getIsEndPoint() {
        return isEndPoint;
    }

    public void setIsEndPoint(Boolean isEndPoint) {
        this.isEndPoint = isEndPoint;
    }

    public Boolean getIsRoundStartPoint() {
        return isRoundStartPoint;
    }

    public void setIsRoundStartPoint(Boolean isRoundStartPoint) {
        this.isRoundStartPoint = isRoundStartPoint;
    }

    public Boolean getIsRoundEndPoint() {
        return isRoundEndPoint;
    }

    public void setIsRoundEndPoint(Boolean isRoundEndPoint) {
        this.isRoundEndPoint = isRoundEndPoint;
    }

    public Double getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Double expectedTime) {
        this.expectedTime = expectedTime;
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
