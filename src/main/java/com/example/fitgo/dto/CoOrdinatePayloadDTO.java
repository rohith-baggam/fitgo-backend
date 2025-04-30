package com.example.fitgo.dto;

import org.springframework.stereotype.Component;
import com.example.fitgo.model.UserBatch;

@Component
public class CoOrdinatePayloadDTO {
    private UserBatch userBatch;
    private double latitude;
    private double longitude;
    private boolean isEndPoint = false;

    public UserBatch getUserBatch() {
        return userBatch;
    }

    public void setUserBatch(UserBatch userBatch) {
        this.userBatch = userBatch;
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
}
