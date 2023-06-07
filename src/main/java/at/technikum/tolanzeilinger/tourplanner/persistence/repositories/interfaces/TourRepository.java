package at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;

import java.util.List;

public interface TourRepository {
    Long create(TourDaoModel tour);
    TourDaoModel read(Long id);
    boolean update(TourDaoModel tour);
    boolean delete(TourDaoModel tour);
    List<TourDaoModel> findAll();
    TourDaoModel findFirst();
    TourDaoModel findFirst(String name);

    TourDaoModel findFirst(long id);
}
