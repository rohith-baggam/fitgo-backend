package com.example.fitgo.dto;

public class CoOrdinateProjectionDTO {
    private long id;
    private double latitude;
    private double longitude;

    public CoOrdinateProjectionDTO(long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
