package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourConverter;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourLogConverter;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourLogRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourLogService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;

import java.util.ArrayList;
import java.util.List;

public class TourLogServiceImpl implements TourLogService {
    private final TourLogRepository tourLogRepository;
    private final TourService tourService;
    private final Logger logger;
    private final EventAggregator eventAggregator;

    private TourLog activeTourLog;
    private long activeTourLogIndex = -1;


    public TourLogServiceImpl(TourLogRepository tourLogRepository, TourService tourService, Logger logger, EventAggregator eventAggregator) {
        this.tourLogRepository = tourLogRepository;
        this.tourService = tourService;
        this.logger = logger;
        this.eventAggregator = eventAggregator;
    }

    @Override
    public void addLog(TourLog tourLog) {
        tourLogRepository.create(TourLogConverter.toTourLogDaoModel(tourLog));
        eventAggregator.publish(Event.TOUR_CHANGED);
    }

    @Override
    public void editLog(TourLog tourLog) {
        tourLogRepository.update(TourLogConverter.toTourLogDaoModel(tourLog));
        eventAggregator.publish(Event.TOUR_CHANGED);
    }

    @Override
    public void deleteLog(TourLog tourLog) {
        tourLogRepository.delete(TourLogConverter.toTourLogDaoModel(tourLog));
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
    public List<TourLog> getAllTourLogsForTour() {
        if(tourService.getActiveTourIndex() >= 0) {
            return tourLogRepository.findAllOfTour(
                        TourConverter.toTourDaoModel(tourService.getActiveTour())
                    ).stream()
                    .map(it -> TourLogConverter.toTourLog(it))
                    .toList();
        } else {
            logger.error("No negative index allowed: " + activeTourLogIndex);
            return null;
        }
    }

    @Override
    public TourLog getActiveTourLog() {
        return activeTourLog;
    }
}
