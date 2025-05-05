package com.example.fitgo.services.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.fitgo.dto.CoOrdinatePayloadDTO;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.repo.CoOrdinatesRepo;

@Component
public class RoundAnalyticUtils {

    private final CoOrdinatesRepo coOrdinatesRepo;

    @Autowired
    public RoundAnalyticUtils(CoOrdinatesRepo coOrdinatesRepo) {
        this.coOrdinatesRepo = coOrdinatesRepo;
    }

    public Optional<CoOrdinates> getLastCoordinateByUserBatch(com.example.fitgo.model.UserBatch userBatch) {
        return coOrdinatesRepo.findTopByUserBatchIdOrderByCreatedDateAsc(userBatch);
    }

    public Optional<CoOrdinates> getFirstCoordinateByUserBatch(com.example.fitgo.model.UserBatch userBatch) {
        return coOrdinatesRepo.findTopByUserBatchIdOrderByCreatedDateDesc(userBatch);
    }

    public double distanceFromCurrentPoint(CoOrdinatePayloadDTO current, CoOrdinates last) {
        if (current == null || last == null)
            return 0.0;
        double lat1 = current.getLatitude();
        double lon1 = current.getLongitude();
        double lat2 = last.getLatitude();
        double lon2 = last.getLongitude();
        return haversine(lat1, lon1, lat2, lon2);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // convert to meters
    }

    public boolean isRoundCompleted(CoOrdinates coOrdinate) {
        if (coOrdinate == null || coOrdinate.getRound() == null)
            return false;
        double currentRoundDistance = coOrdinate.getRoundDistance();
        double threshold = coOrdinate.getRound().getThreashold();
        return currentRoundDistance >= threshold;
    }
}
