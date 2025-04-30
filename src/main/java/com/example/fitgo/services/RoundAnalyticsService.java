package com.example.fitgo.services;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fitgo.dto.CoOrdinatePayloadDTO;
import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.RoundIdTable;
import com.example.fitgo.model.UserBatch;
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
                                isRoundStartPoint(),
                                CoOrdinatePayloadDTO.getIsEndPoint());

                return coOrdinatesRepo.save(newCOrdinate);
        }

        public RoundIdTable createRoundIdTable(CoOrdinates currentCoOrdinate,
                        int roundNumber) {
                RoundIdTable roundIdTable = new RoundIdTable(
                                currentCoOrdinate.getUserBatchId(),
                                currentCoOrdinate,
                                roundNumber);
                roundIdTable.setThreashold(thresholdDistance);
                return roundIdRepo.save(roundIdTable);
        }

        public String displayMessage() {
                return "";
        }

        public RoundIdTable handleFirstCoOrdinateOfJoureny(CoOrdinates currentCoOrdinate) {
                RoundIdTable roundIDInstance = createRoundIdTable(currentCoOrdinate, 1);
                UserBatch userBatch = currentCoOrdinate.getUserBatchId();
                userBatch.setFirstCoOrdinate(currentCoOrdinate);
                roundIDInstance.setRoundStartCoOrdinate(currentCoOrdinate);
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
                currentCoOrdinate.setIsRoundStartPoint(true);
                // currentCoOrdinate.save();

                return roundIDInstance;
        }

        public void handleMidCoOrdinate(
                        CoOrdinates lastCoOrdinate,
                        CoOrdinates currentCoOrdinate,
                        double distanceBetweenCoOrdinates

        ) {

                // ? between last and current co-ordinates
                Duration duration = Duration.between(currentCoOrdinate.getCreatedDate(),
                                lastCoOrdinate.getCreatedDate());
                double timeTakenInSeconds = duration.toMillis() / 1000.0;
                double speedBetweenCoOrdinate = distanceBetweenCoOrdinates / timeTakenInSeconds;
                UserBatch userBatch = currentCoOrdinate.getUserBatchId();

                // ? add values to current CoOrdinate with last CoOrdinate
                currentCoOrdinate.setDistance(distanceBetweenCoOrdinates);
                currentCoOrdinate.setTime(timeTakenInSeconds);
                currentCoOrdinate.setSpeed(speedBetweenCoOrdinate);
                currentCoOrdinate.setLastCoOrdinate(lastCoOrdinate);
                // ? distance time speed from jourent start point
                Optional<CoOrdinates> getLastStarOrdinates = coOrdinatesRepo
                                .findTopByUserBatchIdOrderByCreatedDateDesc(currentCoOrdinate.getUserBatchId());
                CoOrdinates lastStartCoOrdinate = getLastStarOrdinates.get();
                // ? this distance comes from sum of distance between current and last
                // coOrdinates + sum of distances from start point by incrementing eventually
                // with each coOrdinate

                double lastStartCoOrdinateDistance = distanceBetweenCoOrdinates
                                + lastStartCoOrdinate.getTotalDistance();
                double lastStartCoOrdinateTimeTaken = lastStartCoOrdinate.getTotalTime() + timeTakenInSeconds;

                double roundStartCoOrdinateSpeed = lastStartCoOrdinateDistance / lastStartCoOrdinateTimeTaken;

                // ? add values to current CoOrdinate with Joureny Start Point

                currentCoOrdinate.setTotalDistance(
                                distanceBetweenCoOrdinates);
                currentCoOrdinate.setTotalTime(
                                lastStartCoOrdinateTimeTaken);
                currentCoOrdinate.setTotalSpeed(
                                roundStartCoOrdinateSpeed);
                Optional<CoOrdinates> getRoundStartCoOrdinate = coOrdinatesRepo
                                .findTopByRoundOrderByCreatedDateAsc(currentCoOrdinate.getRound());
                CoOrdinates roundStartCoOrdinate = getRoundStartCoOrdinate.get();
                double distance = haverseindistanceBetweenToPoint(
                                currentCoOrdinate.getLatitude(),
                                currentCoOrdinate.getLongitude(),
                                roundStartCoOrdinate.getLatitude(),
                                roundStartCoOrdinate.getLongitude());

                if (isRoundCompleted(currentCoOrdinate)) {
                        // ? this means he far from the Round Start CoOrdinate

                        /*
                         * this distance comes from sum of distance between current and last
                         * coOrdinates + sum of distances from start point of the round by incrementing
                         * eventually with each coOrdinate
                         */
                        // ? distance time speed from round start coOrdinate

                        Optional<CoOrdinates> getRoundStartCoOrdinate = coOrdinatesRepo
                                        .findTopByRoundOrderByCreatedDateDesc(
                                                        currentCoOrdinate.getRound());
                        CoOrdinates roundStartCoOrdinate = getRoundStartCoOrdinate.get();
                        double distanceFromRoundStartCoOrdinate = distanceBetweenCoOrdinates
                                        + roundStartCoOrdinate.getRoundDistance();
                        currentCoOrdinate.setRoundDistance(
                                        distanceFromRoundStartCoOrdinate);
                        double timeTakenFromRoundStartCoOrdinate = timeTakenInSeconds
                                        + roundStartCoOrdinate.getRoundTime();
                        currentCoOrdinate.setRoundTime(
                                        timeTakenFromRoundStartCoOrdinate);
                        currentCoOrdinate.setRoundSpeed(
                                        distanceFromRoundStartCoOrdinate / timeTakenFromRoundStartCoOrdinate);

                } else {
                        // ? this means he is near to start point where less than threshold so we can
                        // set round as completed
                        RoundIdTable lastCoOrdinateRoundIdTable = lastCoOrdinate.getRound();
                        lastCoOrdinateRoundIdTable.setRoundEndCoOrdinate(currentCoOrdinate);
                        // if distance is less than thresh then it mean't user is near to start point
                        // and round is completed
                        RoundIdTable newRound = new RoundIdTable(
                                        currentCoOrdinate.getUserBatchId(),
                                        currentCoOrdinate,
                                        // add round number by incrementing with previous round
                                        lastCoOrdinateRoundIdTable.getRoundNumber() + 1);

                        // newRound.save();
                        // currentRound.save()

                        // ? and get previous coOrdinate and add it as roundEndCoOrdinate to its
                        lastCoOrdinateRoundIdTable.setRoundEndCoOrdinate(lastCoOrdinate);

                        currentCoOrdinate.setIsRoundStartPoint(true);
                        // ? as the current coOrdinate is the start point of the round so last
                        // CoOrdinate will be end point of previous round
                        lastCoOrdinate.setIsRoundEndPoint(true);
                        // appended instance and make sure both instance have different roundId

                }

        }

        public void handleRoundLogic() {
                Optional<CoOrdinates> getLastCoOrdinate = getLastCoordinateByUserBatch(
                                coOrdinatePayloadDTO.getUserBatch());
                CoOrdinates lastCoOrdinate = getLastCoOrdinate.get();
                double distance = distanceFromCurrentPoint(
                                lastCoOrdinate);

                CoOrdinates currentCoOrdinate = createCoOrdinates();
                if (isFirstCoOrdinateOfJoureny() & lastCoOrdinate == null) {
                        handleFirstCoOrdinateOfJoureny(currentCoOrdinate);

                } else {
                        if (coOrdinatePayloadDTO.getIsEndPoint()) {

                                handleMidCoOrdinate(
                                                lastCoOrdinate,
                                                currentCoOrdinate,
                                                distance);

                        }

                }

        }
}
