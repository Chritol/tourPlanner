package at.technikum.tolanzeilinger.tourplanner.service.helperServices;

import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import at.technikum.tolanzeilinger.tourplanner.properties.PropertyLoader;

public class MapquestUrlBuilderService {
    private PropertyLoader properties;

    public MapquestUrlBuilderService(PropertyLoader properties) {
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

    public String buildMapUrl(Tour tour) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://www.mapquestapi.com/staticmap/v5/map?")
                .append("key=").append(properties.getProperty("mapquest.apikey"))
                .append("&session=");

        return stringBuilder.toString();
    }
}