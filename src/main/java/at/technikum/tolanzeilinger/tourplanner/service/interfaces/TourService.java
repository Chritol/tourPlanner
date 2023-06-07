package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import javafx.scene.image.Image;

import java.util.List;

public interface TourService {
    void addTour(Tour tour);

    void setActiveTourIndex(long index);

    long getActiveTourIndex();

    Image loadTourImage();

    Tour getActiveTour();

    Image getActiveImage();

    List<Tour> getTours();
}
