package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.TourLog;

import java.util.List;

public interface TourLogService {
    void addLog(TourLog tourLog);

    void editLog(TourLog tourLog);

    void deleteLog(TourLog tourLog);

    void setActiveTourLogIndex(long index);

    long getActiveTourLogIndex();

    List<TourLog> getAllTourLogs();

    List<TourLog> getAllTourLogsForActiveTour();

    TourLog getActiveTourLog();
}
