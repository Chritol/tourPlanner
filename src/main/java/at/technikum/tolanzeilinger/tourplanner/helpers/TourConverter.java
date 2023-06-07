package at.technikum.tolanzeilinger.tourplanner.helpers;

import at.technikum.tolanzeilinger.tourplanner.model.tours.Hilltype;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Transportation;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationType;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;

public class TourConverter {
    public static TourDaoModel toTourDaoModel(Tour tour) {
        return new TourDaoModel(
                tour.getName(),
                tour.getDescription(),
                tour.getFrom(),
                tour.getTo(),
                TransportationType.valueOf(tour.getTransportation().name()),
                tour.getDistance(),
                tour.getEstimatedTime(),
                HillType.valueOf(tour.getHilliness().name())
        );
    }

    public static Tour toTour(TourDaoModel tourDaoModel) {
        if(tourDaoModel == null) return null;

        Tour tour = new Tour(
                tourDaoModel.getName(),
                tourDaoModel.getDescription(),
                tourDaoModel.getDestinationFrom(),
                tourDaoModel.getDestinationTo()
        );
        tour.setTransportation(Transportation.valueOf(tourDaoModel.getTransportationType().name()));
        tour.setDistance(tourDaoModel.getDistance());
        tour.setEstimatedTime(tourDaoModel.getEstimatedTime());
        tour.setHilliness(Hilltype.valueOf(tourDaoModel.getHillType().name()));
        return tour;
    }
}
