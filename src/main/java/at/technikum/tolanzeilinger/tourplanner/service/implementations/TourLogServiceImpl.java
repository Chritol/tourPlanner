package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourConverter;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourLogConverter;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.model.enums.ChildFriendliness;
import at.technikum.tolanzeilinger.tourplanner.model.enums.Popularity;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourLogRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourLogService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;

import java.util.ArrayList;
import java.util.List;

public class TourLogServiceImpl implements TourLogService {
    private final TourLogRepository tourLogRepository;
    private final TourRepository tourRepository;
    private final TourService tourService;
    private final Logger logger;
    private final EventAggregator eventAggregator;

    private TourLog activeTourLog;
    private long activeTourLogIndex = -1;


    public TourLogServiceImpl(TourLogRepository tourLogRepository, TourService tourService, Logger logger, EventAggregator eventAggregator, TourRepository tourRepository) {
        this.tourLogRepository = tourLogRepository;
        this.tourService = tourService;
        this.logger = logger;
        this.eventAggregator = eventAggregator;
        this.tourRepository = tourRepository;
    }

    @Override
    public void addLog(TourLog tourLog) {
        tourLogRepository.create(TourLogConverter.toTourLogDaoModel(tourLog));
        updateComputedValues(tourLog.getTour());
        eventAggregator.publish(Event.TOUR_CHANGED);
    }

    @Override
    public void editLog(TourLog tourLog) {
        tourLogRepository.update(TourLogConverter.toTourLogDaoModel(tourLog));
        updateComputedValues(tourLog.getTour());
        eventAggregator.publish(Event.TOUR_CHANGED);
    }

    @Override
    public void deleteLog(TourLog tourLog) {
        tourLogRepository.delete(TourLogConverter.toTourLogDaoModel(tourLog));
        updateComputedValues(tourLog.getTour());
        eventAggregator.publish(Event.TOUR_CHANGED);
    }

    @Override
    public void setActiveTourLogIndex(long index) {
        activeTourLogIndex = index;

        if(index >= 0) {
            activeTourLog = TourLogConverter.toTourLog(tourLogRepository.find(index));
        } else {
            logger.warn("Deselected active Tour");
            activeTourLog = null;
        }

        eventAggregator.publish(Event.ACTIVE_TOUR_CHANGED);
    }

    @Override
    public long getActiveTourLogIndex() {
        return activeTourLogIndex;
    }

    @Override
    public List<TourLog> getAllTourLogs() {
        List<TourLog> tourLogs = new ArrayList<>();
        List<TourLogDaoModel> tourDaoLogs = tourLogRepository.findAll();

        for(TourLogDaoModel tourDaoLog : tourDaoLogs) {
            tourLogs.add(TourLogConverter.toTourLog(tourDaoLog));
        }

        return tourLogs;
    }

    @Override
    public List<TourLog> getAllTourLogsForActiveTour() {
        if(tourService.getActiveTourIndex() >= 0) {
            return getAllTourLogsForTour(tourService.getActiveTour());
        } else {
            logger.error("No negative index allowed: " + activeTourLogIndex);
            return null;
        }
    }

    @Override
    public TourLog getActiveTourLog() {
        return activeTourLog;
    }

    private List<TourLog> getAllTourLogsForTour(Tour tour) {
        return tourLogRepository
                .findAllOfTour(TourConverter.toTourDaoModel(tour))
                .stream()
                .map(it -> TourLogConverter.toTourLog(it))
                .toList();
    }

    private void updateComputedValues(Tour tour) {
        if(tour != null) {
            List<TourLog> logs = getAllTourLogsForTour(tour);

            tour.setChildFriendliness(ChildFriendliness.getStatus(tour, logs));
            tour.setPopularity(Popularity.getPopularity(logs != null ? logs.size() : 0));

            tourRepository.update(TourConverter.toTourDaoModel(tour));
        } else {
            logger.warn("Updating computed values for tour failed, because tour was null");
        }
    }
}
