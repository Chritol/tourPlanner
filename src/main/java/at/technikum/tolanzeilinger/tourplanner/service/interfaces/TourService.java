package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import javafx.scene.image.Image;

public interface TourService {
    void addTour(Tour tour);

    void setActiveTourIndex(long index);

    long getActiveTourIndex();

    Image loadTourImage();

    Tour getActiveTour();

    Image getActiveImage();
}
