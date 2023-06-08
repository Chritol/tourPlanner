package at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;

import java.util.List;

public interface TourLogRepository {
    Long create(TourLogDaoModel tour);
    TourLogDaoModel find(Long id);
    boolean update(TourLogDaoModel tour);
    boolean delete(TourLogDaoModel tour);
    List<TourLogDaoModel> findAllOfTour(TourDaoModel tour);
    List<TourLogDaoModel> findAll();
}
