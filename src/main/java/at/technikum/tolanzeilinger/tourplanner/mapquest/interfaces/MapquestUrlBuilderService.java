package at.technikum.tolanzeilinger.tourplanner.mapquest.interfaces;

public interface MapquestUrlBuilderService {
    String buildDirectionsUrl(String from, String to);
    String buildMapImageUrl(String sessionId);
}
