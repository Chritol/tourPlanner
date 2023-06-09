package at.technikum.tolanzeilinger.tourplanner.persistence.dao.helpers;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.DifficultyDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;

import java.time.LocalDateTime;

public class TourLogDaoModelValidator {
    public static boolean isValid(at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel model) {
        return
                model != null
                && isValidTour(model.getTour())
                && isValidLogDateTime(model.getLogDateTime())
                && isValidComment(model.getComment())
                && isValidDifficulty(model.getDifficulty())
                && isValidTotalTime(model.getTotalTime())
                && isValidRating(model.getRating());
    }

    private static boolean isValidTour(TourDaoModel tour) {
        return tour != null;
    }

    private static boolean isValidLogDateTime(LocalDateTime logDateTime) {
        return logDateTime != null;
    }

    private static boolean isValidComment(String comment) {
        return comment != null; // No length validation for TEXT column
    }

    private static boolean isValidDifficulty(DifficultyDaoEnum difficultyDaoEnum) {
        return difficultyDaoEnum != null;
    }

    private static boolean isValidTotalTime(Integer totalTime) {
        return totalTime != null && totalTime >= 0;
    }

    private static boolean isValidRating(Integer rating) {
        return rating != null && rating >= 0;
    }
}
