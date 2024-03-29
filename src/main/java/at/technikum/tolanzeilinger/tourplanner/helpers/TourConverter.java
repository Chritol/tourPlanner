package at.technikum.tolanzeilinger.tourplanner.helpers;

import at.technikum.tolanzeilinger.tourplanner.model.enums.ChildFriendliness;
import at.technikum.tolanzeilinger.tourplanner.model.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.enums.Popularity;
import at.technikum.tolanzeilinger.tourplanner.model.enums.Transportation;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.ChildFriendlinessDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillTypeDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.PopularityDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationTypeDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;

public class TourConverter {
    public static TourDaoModel toTourDaoModel(Tour tour) {
        if(tour == null) return null;

        TourDaoModel daoModel = new TourDaoModel(
                tour.getName(),
                tour.getDescription(),
                tour.getFrom(),
                tour.getTo(),
                TransportationTypeDaoEnum.valueOf(tour.getTransportation().name()),
                tour.getDistance(),
                tour.getEstimatedTime(),
                HillTypeDaoEnum.valueOf(tour.getHilliness().name()),
                PopularityDaoEnum.valueOf(tour.getPopularity().name()),
                ChildFriendlinessDaoEnum.valueOf(tour.getChildFriendliness().name())
        );

        if(tour.getId() != null)
            daoModel.setId(tour.getId());

        return daoModel;
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
        tour.setHilliness(HillType.valueOf(tourDaoModel.getHillType().name()));
        tour.setId(tourDaoModel.getId());
        tour.setPopularity(Popularity.valueOf(tourDaoModel.getPopularityDaoEnum().name()));
        tour.setChildFriendliness(ChildFriendliness.valueOf(tourDaoModel.getChildFriendlinessDaoEnum().name()));
        return tour;
    }
}
