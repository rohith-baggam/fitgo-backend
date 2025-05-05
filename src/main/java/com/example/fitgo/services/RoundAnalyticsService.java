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
public class RoundAnalyticsService {
        @Autowired
        private CoOrdinatesRepo coOrdinatesRepo;
        @Autowired
        private UserBatchRepo userBatchRepo;
        @Autowired
        private RoundIdRepo roundIdRepo;
        @Autowired
        private UserBatchService userBatchService;

        private static final double THRESHOLD_DISTANCE = 0.5;
        @Autowired
        private RoundAnalyticUtils roundAnalyticUtils;

        // @Autowired
        // public RoundAnalyticsService(
        // CoOrdinatePayloadDTO coOrdinatePayloadDTO,
        // CoOrdinatesRepo coOrdinatesRepo,
        // UserBatchRepo userBatchRepo,
        // RoundIdRepo roundIdRepo) {
        // super(coOrdinatePayloadDTO, coOrdinatesRepo);
        // this.coOrdinatePayloadDTO = coOrdinatePayloadDTO;
        // this.coOrdinatesRepo = coOrdinatesRepo;
        // this.userBatchRepo = userBatchRepo;
        // this.roundIdRepo = roundIdRepo;
        // }

        public CoOrdinates createCoOrdinate(
                        CoOrdinatePayloadDTO coOrdinatePayloadDTO,
                        boolean isFirstCoOrdinate,
                        boolean isRoundStartPoint) {
                System.out.println("coOrdinatePayloadDTO " + coOrdinatePayloadDTO.getLatitude());
                System.out.println(
                                "isFirstCoOrdinate " + isFirstCoOrdinate + " isRoundStartPoint " + isRoundStartPoint);
                CoOrdinates coOrdinate = new CoOrdinates(
                                coOrdinatePayloadDTO.getLatitude(),
                                coOrdinatePayloadDTO.getLongitude(),
                                userBatchService.getUserBatchById(coOrdinatePayloadDTO.getBatchId()),
                                isFirstCoOrdinate,
                                isRoundStartPoint,
                                coOrdinatePayloadDTO.getIsEndPoint());
                System.out.println(12.12);
                return coOrdinatesRepo.save(coOrdinate);
        }

        public RoundIdTable createRoundIdTable(CoOrdinates coOrdinate, int roundNumber) {
                RoundIdTable round = new RoundIdTable(coOrdinate.getUserBatchId(), coOrdinate, roundNumber);
                round.setThreashold(THRESHOLD_DISTANCE);
                return roundIdRepo.save(round);
        }

        public RoundIdTable handleFirstCoOrdinateOfJourney(CoOrdinates coOrdinate) {
                RoundIdTable round = createRoundIdTable(coOrdinate, 1);
                UserBatch userBatch = coOrdinate.getUserBatchId();
                System.out.println("Before " + coOrdinate.getRound());
                userBatch.setFirstCoOrdinate(coOrdinate);

                coOrdinate.setRound(round);
                coOrdinate.setDistance(0.0);
                coOrdinate.setTime(0.0);
                coOrdinate.setSpeed(0.0);
                coOrdinate.setTotalDistance(0.0);
                coOrdinate.setTotalTime(0.0);
                coOrdinate.setTotalSpeed(0.0);
                coOrdinate.setRoundDistance(0.0);
                coOrdinate.setRoundTime(0.0);
                coOrdinate.setRoundSpeed(1.0);
                coOrdinate.setIsRoundStartPoint(true);
                System.out.println("round : " + round);
                System.out.println("After : " + coOrdinate.getRound());
                coOrdinatesRepo.save(coOrdinate);
                return round;
        }

        public void handleMidCoOrdinate(
                        CoOrdinates lastCoOrdinate,
                        CoOrdinates currentCoOrdinate,
                        double distanceBetweenCoOrdinates) {
                System.out.println(13.1);
                Duration duration = calculateDuration(currentCoOrdinate, lastCoOrdinate);
                System.out.println(13.2);
                double timeTakenInSeconds = duration.toMillis() / 1000.0;
                if (lastCoOrdinate.getRound() != null) {
                        System.out.println(13.21);
                        if (lastCoOrdinate.getRound() != null) {
                                System.out.println(13.22);
                                currentCoOrdinate.setRound(lastCoOrdinate.getRound());
                        } else {
                                System.out.println(13.23);
                                currentCoOrdinate.setRound(lastCoOrdinate.getLastCoOrdinate().getRound());
                        }
                }
                System.out.println(13.24);
                coOrdinatesRepo.save(currentCoOrdinate);
                double speedBetweenCoordinates = calculateSpeed(distanceBetweenCoOrdinates, timeTakenInSeconds);
                populateCoordinateWithLastData(currentCoOrdinate, lastCoOrdinate, distanceBetweenCoOrdinates,
                                timeTakenInSeconds, speedBetweenCoordinates);
                System.out.println(13.3);
                populateJourneyMetrics(currentCoOrdinate, distanceBetweenCoOrdinates, timeTakenInSeconds);
                System.out.println(13.4 + " " + roundAnalyticUtils.isRoundCompleted(currentCoOrdinate));
                if (!roundAnalyticUtils.isRoundCompleted(currentCoOrdinate)) {
                        System.out.println(13.5);
                        updateRoundJourneyMetrics(currentCoOrdinate, distanceBetweenCoOrdinates, timeTakenInSeconds);
                } else {
                        System.out.println(13.6);
                        System.out.println("lastCoOrdinate ");
                        System.out.println(lastCoOrdinate.getId() + " " + currentCoOrdinate.getId());
                        completeCurrentRoundAndStartNew(lastCoOrdinate, currentCoOrdinate);
                }
        }

        private Duration calculateDuration(CoOrdinates current, CoOrdinates last) {
                return Duration.between(last.getCreatedDate(), current.getCreatedDate());
        }

        private double calculateSpeed(double distance, double timeInSeconds) {
                return timeInSeconds > 0 ? distance / timeInSeconds : 0;
        }

        private void populateCoordinateWithLastData(
                        CoOrdinates current,
                        CoOrdinates last,
                        double distance,
                        double time,
                        double speed) {
                current.setDistance(distance);
                current.setTime(time);
                current.setSpeed(speed);
                System.out.println('a');
                if (last != null) {
                        System.out.println('b');
                        current.setLastCoOrdinate(last);

                }

                coOrdinatesRepo.save(current);
        }

        private void populateJourneyMetrics(
                        CoOrdinates current,
                        double distanceBetween,
                        double timeTaken) {
                System.out.println(13.31);
                Optional<CoOrdinates> optStart = coOrdinatesRepo
                                .findTopByUserBatchIdOrderByCreatedDateDesc(current.getUserBatchId());
                System.out.println(13.32);
                if (optStart.isEmpty())
                        return;
                System.out.println(13.34);
                CoOrdinates journeyStart = optStart.get();
                System.out.println(13.35);
                System.out.println("distanceBetween : " + distanceBetween);
                System.out.println("journeyStart.getTotalDistance() : " + journeyStart.getTotalDistance());
                double totalDistance = distanceBetween + journeyStart.getTotalDistance();
                System.out.println(13.36);
                System.out.println("timeTaken : " + timeTaken);
                System.out.println("journeyStart.getTotalTime() : " + journeyStart.getTotalTime());
                double totalTime = timeTaken + journeyStart.getTotalTime();
                System.out.println(13.37);
                double totalSpeed = calculateSpeed(totalDistance, totalTime);
                System.out.println(13.38);
                current.setTotalDistance(totalDistance);
                current.setTotalTime(totalTime);
                current.setTotalSpeed(totalSpeed);
        }

        private void updateRoundJourneyMetrics(
                        CoOrdinates current,
                        double distanceBetween,
                        double timeTaken) {
                Optional<CoOrdinates> optRoundStart = coOrdinatesRepo
                                .findTopByRoundOrderByCreatedDateDesc(current.getRound());
                if (optRoundStart.isEmpty())
                        return;

                CoOrdinates roundStart = optRoundStart.get();
                double roundDistance = distanceBetween + roundStart.getRoundDistance();
                double roundTime = timeTaken + roundStart.getRoundTime();
                double roundSpeed = calculateSpeed(roundDistance, roundTime);

                current.setRoundDistance(roundDistance);
                current.setRoundTime(roundTime);
                current.setRoundSpeed(roundSpeed);
                coOrdinatesRepo.save(current);
        }

        private void completeCurrentRoundAndStartNew(
                        CoOrdinates last,
                        CoOrdinates current) {
                System.out.println(141.1 + " " + last.getId());

                RoundIdTable previousRound = last.getRound();
                previousRound.setRoundEndCoOrdinate(last);
                System.out.println(14.1);
                RoundIdTable newRound = new RoundIdTable(
                                current.getUserBatchId(),
                                current,
                                previousRound.getRoundNumber() + 1);
                System.out.println(14.2);
                current.setIsRoundStartPoint(true);
                System.out.println(14.3);
                last.setIsRoundEndPoint(true);
                System.out.println(14.4);
                // Additional logic to save newRound if needed
        }

        public CoOrdinates handleRoundLogic(
                        CoOrdinatePayloadDTO coOrdinatePayloadDTO

        ) {
                System.out.println(11);
                Optional<UserBatch> userBatch = userBatchRepo.findByBatchId(coOrdinatePayloadDTO.getBatchId());
                Optional<CoOrdinates> lastOpt = coOrdinatesRepo.findTopByUserBatchIdOrderByCreatedDateDesc(
                                userBatch.get());
                System.out.println(12);
                System.out.println(12.45665);
                // System.out.println("1234 : " + lastOpt.get().getId());
                boolean isRoundStartPoint = false;
                boolean isFirstCoOrdinate = coOrdinatePayloadDTO.getIsStartPoint();
                System.out.println(12.1);
                CoOrdinates current = createCoOrdinate(
                                coOrdinatePayloadDTO, isFirstCoOrdinate,
                                isRoundStartPoint);
                System.out.println(13);
                if (isFirstCoOrdinate) {
                        System.out.println(14);
                        handleFirstCoOrdinateOfJourney(current);
                } else {
                        System.out.println(15);

                        System.out.println("after " + lastOpt.get());
                        // getFirstCoordinateByUserBatch
                        // getLastCoordinateByUserBatch
                        System.out.println(16);
                        System.out.println("lastOpt : " + lastOpt.isPresent() + " lastOpt " + lastOpt.get().getRound());
                        if (!coOrdinatePayloadDTO.getIsEndPoint() && lastOpt.isPresent()) {
                                System.out.println(17);
                                CoOrdinates last = lastOpt.get();
                                double distance = roundAnalyticUtils.distanceFromCurrentPoint(
                                                coOrdinatePayloadDTO,
                                                last);
                                handleMidCoOrdinate(last, current, distance);
                                System.out.println(20);
                        } else {
                                System.out.println(18);
                        }
                }
                return current;
        }
}
