package com.example.fitgo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ROUNDIDTABLE")
public class RoundIdTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BATCH_ID", nullable = false)
    private UserBatch batchId;

    @Column(name = "ROUND_NUMBER")
    private int roundNumber;

    @ManyToOne
    @JoinColumn(name = "ROUND_START_COORDINATE")
    private CoOrdinates roundStartCoOrdinate;

    @ManyToOne
    @JoinColumn(name = "ROUND_END_COORDINATE")
    private CoOrdinates roundEndCoOrdinate;

    @Column(name = "TOTAL_DISTANCE")
    private Double totalDistance;

    @Column(name = "TOTAL_TIME")
    private Double totalTime;

    @Column(name = "TOTAL_SPEED")
    private Double totalSpeed;

    @Column(name = "THREASHOLD")
    private Double threashold;

    // default constructor
    public RoundIdTable() {

    }

    // Constructor
    public RoundIdTable(UserBatch userBatch, CoOrdinates roundStartCoOrdinate, int roundNumber) {
        this.batchId = userBatch;
        this.roundStartCoOrdinate = roundStartCoOrdinate;
        this.roundNumber = roundNumber;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBatch getBatchId() {
        return batchId;
    }

    public void setBatchId(UserBatch batchId) {
        this.batchId = batchId;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public CoOrdinates getRoundStartCoOrdinate() {
        return roundStartCoOrdinate;
    }

    public void setRoundStartCoOrdinate(CoOrdinates roundStartCoOrdinate) {
        this.roundStartCoOrdinate = roundStartCoOrdinate;
    }

    public CoOrdinates getRoundEndCoOrdinate() {
        return roundEndCoOrdinate;
    }

    public void setRoundEndCoOrdinate(CoOrdinates roundEndCoOrdinate) {
        this.roundEndCoOrdinate = roundEndCoOrdinate;
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

    public Double getThreashold() {
        return threashold;
    }

    public void setThreashold(Double threashold) {
        this.threashold = threashold;
    }
}
