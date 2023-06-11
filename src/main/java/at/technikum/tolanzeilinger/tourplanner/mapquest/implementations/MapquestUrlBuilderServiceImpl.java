package at.technikum.tolanzeilinger.tourplanner.mapquest.implementations;

import at.technikum.tolanzeilinger.tourplanner.constants.DefaultConstants;
import at.technikum.tolanzeilinger.tourplanner.constants.PropertyConstants;
import at.technikum.tolanzeilinger.tourplanner.mapquest.interfaces.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.PropertyLoaderService;

public class MapquestUrlBuilderServiceImpl implements MapquestUrlBuilderService {
    private final PropertyLoaderService propertyLoaderService;

    public MapquestUrlBuilderServiceImpl(PropertyLoaderService propertyLoaderService) {
        this.propertyLoaderService = propertyLoaderService;
    }

    public String buildDirectionsUrl(String from, String to) {
        String stringBuilder = DefaultConstants.MAPQUEST_ROUTE_URL +
                "key=" + propertyLoaderService.getProperty(PropertyConstants.MAPQUEST_APIKEY) +
                "&from=" + from.replaceAll(" ", "") +
                "&to=" + to.replaceAll(" ", "") +
                "&unit=K";

        return stringBuilder;
    }

    public String buildMapImageUrl(String sessionId) {
        String stringBuilder = DefaultConstants.MAPQUEST_MAP_URL +
                "key=" + propertyLoaderService.getProperty(PropertyConstants.MAPQUEST_APIKEY) +
                "&session=" + sessionId;

        return stringBuilder;
    }
}