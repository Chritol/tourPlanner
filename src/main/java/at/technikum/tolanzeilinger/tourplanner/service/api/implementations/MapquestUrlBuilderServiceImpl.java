package at.technikum.tolanzeilinger.tourplanner.service.api.implementations;

import at.technikum.tolanzeilinger.tourplanner.constants.DefaultConstants;
import at.technikum.tolanzeilinger.tourplanner.constants.PropertyConstants;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.service.api.interfaces.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.PropertyLoaderService;

public class MapquestUrlBuilderServiceImpl implements MapquestUrlBuilderService {
    private PropertyLoaderService propertyLoaderService;

    public MapquestUrlBuilderServiceImpl(PropertyLoaderService propertyLoaderService) {
        this.propertyLoaderService = propertyLoaderService;
    }

    public String buildDirectionsUrl(String from, String to) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DefaultConstants.MAPQUEST_ROUTE_URL)
                .append("key=").append(propertyLoaderService.getProperty(PropertyConstants.MAPQUEST_APIKEY))
                .append("&from=").append(from.replaceAll(" ", ""))
                .append("&to=").append(to.replaceAll(" ", ""))
                .append("&unit=K");

        return stringBuilder.toString();
    }

    public String buildMapImageUrl(String sessionId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DefaultConstants.MAPQUEST_MAP_URL)
                .append("key=").append(propertyLoaderService.getProperty(PropertyConstants.MAPQUEST_APIKEY))
                .append("&session=").append(sessionId);

        return stringBuilder.toString();
    }
}