package com.example.fitgo.dto;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class CoOrdinatePayloadDTO {
    private UUID batchId;
    private double latitude;
    private double longitude;
    private boolean isEndPoint;
    private boolean isStartPoint;

    public UUID getBatchId() {
        return batchId;
    }

    public void setBatchId(UUID batchId) {
        this.batchId = batchId;
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

    public boolean getIsEndPoint() {
        return isEndPoint;
    }

    public void setIsEndPoint(boolean isEndPoint) {
        this.isEndPoint = isEndPoint;
    }

    public void setIsStartPoint(boolean isStartPoint) {
        this.isStartPoint = isStartPoint;
    }

    public boolean getIsStartPoint() {
        return this.isStartPoint;
    }

}
