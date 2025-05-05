package com.example.fitgo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CO_ORDINATES_TABLE")
public class CoOrdinates {
    // each coOrdinate means a step taken by a person
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;
    // each day or each joureny will have different batch id. If a person start a
    // new joureny or each day when he goes to walking or jogging he clicks on start
    // button on android device which create a new uuid and sends to backend through
    // out the joureny, as each day it will have same batch id
    @ManyToOne
    @JoinColumn(name = "USER_BATCH_ID")
    private UserBatch userBatchId;
    // this is the last coOrdinate or last step
    @ManyToOne
    @JoinColumn(name = "LAST_COORDINATE")
    private CoOrdinates lastCoOrdinate;
    // if a user completed once circle or if user reaches his intial point this will
    // be marked as as single round
    @ManyToOne
    @JoinColumn(name = "ROUND")
    private RoundIdTable round;
    // * one joureny is considered with UserBatchId where batch id is unique or
    // there will be only one batch Id, and Once Joureny has multiple rounds

    // this is the distance between two consecutive co-ordinate, or will running or
    // between API triggers will API's are polling

    // distance between 2 steps
    @Column(name = "DISTANCE")
    private Double distance = 0.0;

    // time taken by distance
    @Column(name = "TIME")
    private Double time = 0.0;

    // distance covered in unit of time
    @Column(name = "SPEED")
    private Double speed = 0.0;

    // this is the entire joureny. If a person started walking till he start stops
    // walking this calculates distance from starting point to here

    @Column(name = "TOTAL_DISTANCE")
    private Double totalDistance = 0.0;

    @Column(name = "TOTAL_TIME")
    private Double totalTime = 0.0;

    @Column(name = "TOTAL_SPEED")
    private Double totalSpeed = 0.0;

    // consider if a person is walking in a park, when person reaches near to his
    // initial person which is less than threshold point it will be considered as a
    // round & this will store distance from round point from round start point

    @Column(name = "ROUND_DISTANCE")
    private Double roundDistance = 0.0;

    @Column(name = "ROUND_TIME")
    private Double roundTime = 0.0;

    @Column(name = "ROUND_SPEED")
    private Double roundSpeed = 0.0;

    // when user clicks on start or first point of a UserBatch is considered as
    // first step or coOrdinate
    @Column(name = "IS_START_POINT")
    private Boolean isStartPoint = false;

    // when user clicks on end or end point of a UserBatch is considered as
    // last or end step or coOrdinate
    @Column(name = "IS_END_POINT")
    private Boolean isEndPoint = false;

    @Column(name = "IS_ROUND_START_POINT")
    private Boolean isRoundStartPoint = false;

    @Column(name = "IS_ROUND_END_POINT")
    private Boolean isRoundEndPoint = false;

    // when user is going for second round then analyzing first round, to round how
    // much distance left frm current coordinate and if user moves with current
    // speed then what will be the expected time = current speed/left over distance

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

    public CoOrdinates() {
    }

    // constructors
    public CoOrdinates(
            double latitude,
            double longitude,
            UserBatch userBatchId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userBatchId = userBatchId;
        this.createdDate = LocalDateTime.now(); // optional if you're using @PrePersist
    }

    public CoOrdinates(
            double latitude,
            double longitude,
            UserBatch userBatch,
            boolean isStartPoint,
            boolean isRoundStartPoint) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userBatchId = userBatch;
        this.isStartPoint = isStartPoint;
    }

    public CoOrdinates(
            double latitude,
            double longitude,
            UserBatch userBatch,
            boolean isStartPoint,
            boolean isRoundStartPoint,
            boolean isEndPoint

    ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userBatchId = userBatch;
        this.isStartPoint = isStartPoint;
        this.isEndPoint = isEndPoint;
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
