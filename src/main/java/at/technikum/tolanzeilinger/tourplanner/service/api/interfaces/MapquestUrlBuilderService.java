package at.technikum.tolanzeilinger.tourplanner.service.api.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.Tour;

public interface MapquestUrlBuilderService {
    String buildDirectionsUrl(String from, String to);
    String buildMapImageUrl(String sessionId);
}
