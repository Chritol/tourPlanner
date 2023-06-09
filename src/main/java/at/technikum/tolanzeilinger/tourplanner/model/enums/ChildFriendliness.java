package at.technikum.tolanzeilinger.tourplanner.model.enums;

import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;

import java.util.List;

public enum ChildFriendliness {
    NOT_FOR_CHILDREN("Not ment for children", 3, 100, 10),
    NOT_RECOMMENDED_FOR_CHILDREN("Not recommended for children", 6, 150, 25),
    RECOMMENDED_FOR_CHILDREN("Recommended for children", 10, 1000, 1000);
    private final String textValue;
    private final int maxRating;
    private final int maxTime;
    private final int maxDistance;

    ChildFriendliness(String textValue, int maxRating, int maxTime, int maxDistance) {
        this.textValue = textValue;
        this.maxRating = maxRating;
        this.maxTime = maxTime;
        this.maxDistance = maxDistance;
    }

    public String getTextValue() {
        return textValue;
    }

    public static ChildFriendliness getStatus(Tour tour, List<TourLog> logList) {
        if (tour != null && logList != null && logList.size() > 0) {
            double avgRating = logList.stream()
                    .mapToInt(TourLog::getRating)
                    .average()
                    .orElse(-1);
            int maxTime = logList.stream()
                    .mapToInt(TourLog::getTotalTime)
                    .max()
                    .orElse(-1);
            int maxDistance = tour.getDistance();

            if(maxTime >= 0 && avgRating >= 0) {
                if(avgRating <= NOT_FOR_CHILDREN.maxRating || maxTime >= NOT_FOR_CHILDREN.maxTime || maxDistance >= NOT_FOR_CHILDREN.maxDistance) return NOT_FOR_CHILDREN;
                if(avgRating <= NOT_RECOMMENDED_FOR_CHILDREN.maxRating || maxTime >= NOT_RECOMMENDED_FOR_CHILDREN.maxTime || maxDistance >= NOT_RECOMMENDED_FOR_CHILDREN.maxDistance) return NOT_FOR_CHILDREN;
                if(avgRating <= RECOMMENDED_FOR_CHILDREN.maxRating || maxTime >= RECOMMENDED_FOR_CHILDREN.maxTime || maxDistance >= RECOMMENDED_FOR_CHILDREN.maxDistance) return NOT_FOR_CHILDREN;
            }
        }
        return NOT_FOR_CHILDREN;
    }
}
