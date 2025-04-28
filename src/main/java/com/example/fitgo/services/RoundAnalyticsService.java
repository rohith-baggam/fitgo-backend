package com.example.fitgo.services;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fitgo.dto.CoOrdinatePayloadDTO;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.RoundIdTable;
import com.example.fitgo.repo.CoOrdinatesRepo;
import com.example.fitgo.repo.RoundIdRepo;
import com.example.fitgo.repo.UserBatchRepo;
import com.example.fitgo.services.utils.RoundAnalyticUtils;

@Service
public class RoundAnalyticsService extends RoundAnalyticUtils {

    CoOrdinatePayloadDTO coOrdinatePayloadDTO;
    // CoOrdinates coOrdinate;
    // RoundIdTable roundIdTable;

    RoundIdRepo roundIdRepo;
    private double thresholdDistance = 0.5;

    @Autowired
    public RoundAnalyticsService(
            CoOrdinatePayloadDTO coOrdinatePayloadDTO,
            CoOrdinatesRepo coOrdinatesRepo,
            UserBatchRepo userBatchRepo) {
        super(
                coOrdinatePayloadDTO,
                coOrdinatesRepo);
        this.userBatchRepo = userBatchRepo;
    }

    // public void setCoOrdinate(CoOrdinates coOrdinate) {
    // this.coOrdinate = coOrdinate;
    // }

    // public CoOrdinates getCoOrdinate() {
    // return this.coOrdinate;
    // }

    // public void setRoundIdTable(RoundIdTable roundIdTable) {
    // this.roundIdTable = roundIdTable;
    // }

    // public RoundIdTable getRoundIdTable() {
    // return this.roundIdTable;
    // }

    @Autowired
    private CoOrdinatesRepo coOrdinatesRepo;

    @Autowired
    private UserBatchRepo userBatchRepo;

    /*
     * -> check is it a first point
     * -> if first round
     * -> create new round and return it's instance
     */

    public CoOrdinates createCoOrdinates() {
        CoOrdinates newCOrdinate = new CoOrdinates(
                coOrdinatePayloadDTO.getLatitude(),
                coOrdinatePayloadDTO.getLongitude(),
                coOrdinatePayloadDTO.getUserBatch(),
                isFirstCoOrdinateOfJoureny(),
                isRoundStartPoint());

        return coOrdinatesRepo.save(newCOrdinate);
    }

    public RoundIdTable createRoundIdTable(CoOrdinates currentCoOrdinate) {
        RoundIdTable roundIdTable = new RoundIdTable(
                currentCoOrdinate.getUserBatchId(),
                currentCoOrdinate,
                1);

        return roundIdRepo.save(roundIdTable);
    }

    public String displayMessage() {
        return "";
    }

    public RoundIdTable handleFirstCoOrdinateOfJoureny(CoOrdinates currentCoOrdinate) {
        RoundIdTable roundIDInstance = createRoundIdTable(currentCoOrdinate);

        currentCoOrdinate.setRound(roundIDInstance);
        double nullValue = 0;
        // set null values for value between coOrdinates
        currentCoOrdinate.setDistance(nullValue);
        currentCoOrdinate.setTime(nullValue);
        currentCoOrdinate.setSpeed(nullValue);
        // set null values for total journey
        currentCoOrdinate.setTotalDistance(nullValue);
        currentCoOrdinate.setTotalTime(nullValue);
        currentCoOrdinate.setTotalSpeed(nullValue);
        // set null values for round journey
        currentCoOrdinate.setRoundDistance(nullValue);
        currentCoOrdinate.setRoundTime(nullValue);
        currentCoOrdinate.setRoundSpeed(nullValue);
        return roundIDInstance;
    }

    public void handleMidCoOrdinate(
            CoOrdinates lastCoOrdinate,
            CoOrdinates currentCoOrdinate,
            double distanceBetweenCoOrdinates

    ) {
        double lastCoOrdinateDisntance = lastCoOrdinate.getDistance();
        double lastCoOrdinateTime = lastCoOrdinate.getTime();
        double lastCoOrdinateSpeed = lastCoOrdinate.getSpeed();

        Duration duration = Duration.between(currentCoOrdinate.getCreatedDate(), lastCoOrdinate.getCreatedDate());
        double timeTakenInSeconds = duration.toMillis() / 1000.0;

    }

    public void handleRoundLogic() {
        CoOrdinates currentCoOrdinate = createCoOrdinates();
        if (isFirstCoOrdinateOfJoureny()) {
            handleFirstCoOrdinateOfJoureny(currentCoOrdinate);

        } else {
            Optional<CoOrdinates> getLastCoOrdinate = getLastCoordinateByUserBatch(
                    coOrdinatePayloadDTO.getUserBatch());
            CoOrdinates lastCoOrdinate = getLastCoOrdinate.get();
            double distance = distanceWithCurrentPoint(
                    lastCoOrdinate);
            if (distance < thresholdDistance) {
                // round completed

            } else {
                // this is in the middle of the round

            }

        }

    }
}
