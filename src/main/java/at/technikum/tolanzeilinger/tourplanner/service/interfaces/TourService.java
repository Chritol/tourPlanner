package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import javafx.scene.image.Image;

import java.util.List;

public interface TourService {
    void addTour(Tour tour);

    void setActiveTourIndex(long index);

    long getActiveTourIndex();

    Tour getActiveTour();

    Image getActiveImage();

    void deleteTourByIndex(long id);

    void updateTourByIndex(long id, Tour tour);

    List<Tour> getTours();
}
