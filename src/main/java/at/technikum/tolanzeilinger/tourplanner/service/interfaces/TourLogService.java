package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;

public interface TourLogService {
    void addLog(TourLogDaoModel logDaoModel);
}
