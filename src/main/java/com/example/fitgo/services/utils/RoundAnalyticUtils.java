package com.example.fitgo.services.utils;

import com.example.fitgo.repo.CoOrdinatesRepo;

import java.util.Optional;

import com.example.fitgo.dto.CoOrdinatePayloadDTO;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.RoundIdTable;
import com.example.fitgo.model.UserBatch;

public abstract class RoundAnalyticUtils {

    protected final CoOrdinatesRepo coOrdinatesRepo;
    protected final CoOrdinatePayloadDTO coOrdinatePayloadDTO;

    private double thresholdDistance = 0.5;

    public RoundAnalyticUtils(
            CoOrdinatePayloadDTO coOrdinatePayloadDTO,
            CoOrdinatesRepo coOrdinatesRepo) {
        this.coOrdinatePayloadDTO = coOrdinatePayloadDTO;
        this.coOrdinatesRepo = coOrdinatesRepo;
    }

    public boolean isFirstCoOrdinateOfJoureny() {
        return coOrdinatesRepo.existsByUserBatchId(coOrdinatePayloadDTO.getUserBatch());
    }

    public boolean isRoundStartPoint() {
        return false;
    }

    public Optional<CoOrdinates> lastCoOrdinate() {
        return coOrdinatesRepo.findTopByUserBatchIdOrderByCreatedDateDesc(null);
    }

    public boolean isFirstCoOrdinateOfRound() {
        return coOrdinatesRepo.existsByRound(null);
    }

    public double haversinedistanceBetweenToPoint(double lat1, double lon1, double lat2, double lon2) {
        // Radius of the Earth in kilometers
        final int R = 6371;

        // Convert degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Haversine formula
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate the distance in kilometers
        double distance = R * c;

        return distance; // Distance in kilometers
    }

    public double distanceWithCurrentPoint(
            CoOrdinates coOrdinate) {

        double distance = haversinedistanceBetweenToPoint(
                coOrdinatePayloadDTO.getLatitude(),
                coOrdinatePayloadDTO.getLongitude(),
                coOrdinate.getLatitude(),
                coOrdinate.getLongitude());
        return distance;
    }

    public double distanceWithJourneyStartPoint() {
        if (isFirstCoOrdinateOfJoureny()) {
            return 0;
        }
        Optional<CoOrdinates> coOrdinates = coOrdinatesRepo
                .findTopByUserBatchIdOrderByCreatedDateDesc(coOrdinatePayloadDTO.getUserBatch()); // Corrected here
        CoOrdinates startCoOrdinates = coOrdinates.orElse(null); // Handle Optional correctly
        if (startCoOrdinates == null) {
            return 0;
        }
        return haversinedistanceBetweenToPoint(
                coOrdinatePayloadDTO.getLatitude(),
                coOrdinatePayloadDTO.getLatitude(),
                startCoOrdinates.getLatitude(),
                startCoOrdinates.getLongitude());
    }

    public boolean isRoundCompleted() {
        return distanceWithJourneyStartPoint() <= thresholdDistance;
    }

    // CoOrdinates lastCoOrdinateInstance = coOrdinate2.get();
    // Optional<CoOrdinates> lastCoOrdinate =
    // coOrdinatesRepo.findTopByUserBatchIdOrderByCreatedDateDesc(
    // coOrdinatePayloadDTO.getUserBatch());
    // if (!lastCoOrdinate.isPresent()) {
    // return 0;
    // }
    public double distanceWithCurrentAndLastCoOrdinate() {
        Optional<CoOrdinates> lastCoOrdinate = coOrdinatesRepo.findTopByUserBatchIdOrderByCreatedDateDesc(
                coOrdinatePayloadDTO.getUserBatch());
        if (!lastCoOrdinate.isPresent()) {
            return 0;
        }
        CoOrdinates lastCoOrdinateInstance = lastCoOrdinate.get();
        double distance = haversinedistanceBetweenToPoint(
                coOrdinatePayloadDTO.getLatitude(),
                coOrdinatePayloadDTO.getLongitude(),
                lastCoOrdinateInstance.getLatitude(),
                lastCoOrdinateInstance.getLongitude());
        return distance;
    }

    public double distanceWithCurrentAndRoundFirstCoOrdinate() {

        return 0;
    }

    // 1. Get last CoOrdinate by UserBatch
    public Optional<CoOrdinates> getLastCoordinateByUserBatch(UserBatch userBatch) {
        return coOrdinatesRepo.findTopByUserBatchIdOrderByCreatedDateDesc(userBatch);
    }

}
