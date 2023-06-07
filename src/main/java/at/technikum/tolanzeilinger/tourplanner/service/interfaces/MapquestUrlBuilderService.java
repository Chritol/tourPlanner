package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;

public interface MapquestUrlBuilderService {
    String buildDirectionsUrl(Tour tour);
    String buildMapImageUrl(Tour tour);
}
