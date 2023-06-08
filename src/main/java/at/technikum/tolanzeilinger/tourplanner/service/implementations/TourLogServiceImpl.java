package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourLogConverter;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourLogRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourLogService;

import java.util.List;

public class TourLogServiceImpl implements TourLogService {
    private final TourLogRepository tourLogRepository;
    private final TourRepository tourRepository;
    private final Logger logger;
    private final EventAggregator eventAggregator;

    private TourLog activeTourLog;
    private long activeTourLogIndex = -1;


    public TourLogServiceImpl(TourLogRepository tourLogRepository, TourRepository tourRepository, Logger logger, EventAggregator eventAggregator) {
        this.tourLogRepository = tourLogRepository;
        this.tourRepository = tourRepository;
        this.logger = logger;
        this.eventAggregator = eventAggregator;
    }

    @Override
    public void addLog(TourLog tourLog) {
        tourLogRepository.create(TourLogConverter.toTourLogDaoModel(tourLog));
    }

    @Override
    public void editLog(TourLog tourLog) {
        tourLogRepository.update(TourLogConverter.toTourLogDaoModel(tourLog));
    }

    @Override
    public void deleteLog(TourLog tourLog) {
        tourLogRepository.delete(TourLogConverter.toTourLogDaoModel(tourLog));
    }

    @Override
    public void setActiveTourLogIndex(long index) {
        if(index >= 0) {
            activeTourLogIndex = index;
            activeTourLog = TourLogConverter.toTourLog(tourLogRepository.find(index));
        } else {
            logger.error("No negative index allowed: " + activeTourLogIndex);
        }
    }

    @Override
    public long getActiveTourLogIndex() {
        return activeTourLogIndex;
    }

    @Override
    public List<TourLog> getAllTourLogs() {
        return tourLogRepository
                .findAll()
                .stream()
                .map(it -> TourLogConverter.toTourLog(it))
                .toList();
    }

    @Override
    public List<TourLog> getAllTourLogsForTour() {
        if(activeTourLogIndex >= 0) {
            return tourLogRepository.findAllOfTour(tourRepository.find(activeTourLogIndex))
                    .stream()
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
