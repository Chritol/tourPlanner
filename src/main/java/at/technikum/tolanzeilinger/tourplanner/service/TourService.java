package at.technikum.tolanzeilinger.tourplanner.service;

import at.technikum.tolanzeilinger.tourplanner.model.repositories.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;

// just for talking to the Repository
public class TourService {

    private TourRepository tourRepository;

    private Tour activeTour;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }
}
