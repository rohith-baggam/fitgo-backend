package com.example.fitgo.services;

import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fitgo.model.CoOrdinates;
import com.example.fitgo.model.UserBatch;
import com.example.fitgo.repo.CoOrdinatesRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CoOrdinatesService {
    @Autowired
    private CoOrdinatesRepo repo;

    /**
     * Saves coordinates only if the last saved coordinates for the given
     * UserBatch are different.
     *
     * @param latitude  the latitude to check/save
     * @param longitude the longitude to check/save
     * @param userBatch the associated UserBatch
     * @return the saved CoOrdinates object if saved, or null if skipped
     */
    public CoOrdinates saveIfNotDuplicate(double latitude, double longitude, UserBatch userBatch) {

        Optional<CoOrdinates> last = repo.findTopByUserBatchIdOrderByCreatedDateDesc(userBatch);

        if (last.isPresent()) {
            CoOrdinates lastCoords = last.get();
            if (Double.compare(lastCoords.getLatitude(), latitude) == 0 &&
                    Double.compare(lastCoords.getLongitude(), longitude) == 0) {
                // Same coordinates found, skip saving
                System.out.println(3);
                return null;
            }
        }

        // Coordinates are new, save them
        CoOrdinates newCoords = new CoOrdinates(
                latitude,
                longitude,
                userBatch);
        System.out.println("New CoOrdinate added " + newCoords.getId());
        return repo.save(newCoords);

    }
}
