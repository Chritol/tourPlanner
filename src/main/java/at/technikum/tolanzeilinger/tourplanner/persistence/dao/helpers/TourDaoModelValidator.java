package at.technikum.tolanzeilinger.tourplanner.persistence.dao.helpers;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillTypeDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationTypeDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;

public class TourDaoModelValidator {

    public static boolean isValid(TourDaoModel model) {
        return
                model != null
                && isValidName(model.getName())
                && isValidDescription(model.getDescription())
                && isValidDestination(model.getDestinationFrom())
                && isValidDestination(model.getDestinationTo())
                && isValidTransportationType(model.getTransportationType())
                && isValidDistance(model.getDistance())
                && isValidEstimatedTime(model.getEstimatedTime())
                && isValidHillType(model.getHillType());
    }

    private static boolean isValidName(String name) {
        return name != null && name.length() <= 50;
    }

    private static boolean isValidDescription(String description) {
        return description != null;
    }

    private static boolean isValidDestination(String destination) {
        return destination != null && destination.length() <= 100;
    }

    private static boolean isValidTransportationType(TransportationTypeDaoEnum transportationTypeDaoEnum) {
        return transportationTypeDaoEnum != null;
    }

    private static boolean isValidDistance(Integer distance) {
        return distance != null && distance >= 0;
    }

    private static boolean isValidEstimatedTime(Integer estimatedTime) {
        return estimatedTime != null && estimatedTime >= 0;
    }

    private static boolean isValidHillType(HillTypeDaoEnum hillTypeDaoEnum) {
        return hillTypeDaoEnum != null;
    }
}
