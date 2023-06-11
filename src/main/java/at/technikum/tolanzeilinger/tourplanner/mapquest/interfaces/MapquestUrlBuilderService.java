package at.technikum.tolanzeilinger.tourplanner.mapquest.interfaces;

import at.technikum.tolanzeilinger.tourplanner.mapquest.models.enums.RoadStrategy;
import at.technikum.tolanzeilinger.tourplanner.mapquest.models.enums.TripType;

public interface MapquestUrlBuilderService {
    String buildDirectionsUrl(String from, String to, TripType tripType, RoadStrategy roadStrategy);
    String buildMapImageUrl(String sessionId);
}
