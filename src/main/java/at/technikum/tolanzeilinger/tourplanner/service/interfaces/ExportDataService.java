package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;

import java.util.List;
import java.util.Map;

public interface ExportDataService {
    public void exportToursAndLogs();
}
