package at.technikum.tolanzeilinger.tourplanner.helpers;

import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.DifficultyDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;

public class TourLogConverter {
    public static TourLog toTourLog(TourLogDaoModel daoModel) {
        if(daoModel == null) return null;

        TourLog tourLog = new TourLog(
                daoModel.getId(),
                TourConverter.toTour(daoModel.getTour()),
                daoModel.getLogDateTime(),
                daoModel.getComment(),
                at.technikum.tolanzeilinger.tourplanner.model.enums.Difficulty.valueOf(daoModel.getDifficulty().name()),
                daoModel.getTotalTime(),
                daoModel.getRating()
        );
        return tourLog;
    }

    public static TourLogDaoModel toTourLogDaoModel(TourLog tourLog) {

        TourLogDaoModel daoModel = new TourLogDaoModel(
                TourConverter.toTourDaoModel(tourLog.getTour()),
                tourLog.getLogDateTime(),
                tourLog.getComment(),
                DifficultyDaoEnum.valueOf(tourLog.getDifficulty().name()),
                tourLog.getTotalTime(),
                tourLog.getRating()
        );

        if(tourLog.getId() != null)
            daoModel.setId(tourLog.getId());
        return daoModel;
    }
}
