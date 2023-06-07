package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import at.technikum.tolanzeilinger.tourplanner.properties.PropertyLoader;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.MapquestService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.MapquestUrlBuilderService;

public class MapquestUrlBuilderServiceImpl implements MapquestUrlBuilderService {
    private PropertyLoader properties;

    public MapquestUrlBuilderServiceImpl(PropertyLoader properties) {
        this.properties = properties;
    }

    public String buildDirectionsUrl(Tour tour) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://www.mapquestapi.com/directions/v2/route?")
                .append("key=").append(properties.getProperty("mapquest.apikey"))
                .append("&from=").append(tour.getFrom().getLocationName())
                .append("&to=").append(tour.getTo().getLocationName())
                .append("&unit=K");

        return stringBuilder.toString();
    }

    public String buildMapImageUrl(Tour tour) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://www.mapquestapi.com/staticmap/v5/map?")
                .append("key=").append(properties.getProperty("mapquest.apikey"))
                .append("&session=");

        return stringBuilder.toString();
    }
}