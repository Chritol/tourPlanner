package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import javafx.scene.image.Image;

public interface TourService {
    public void addTour(Tour tour);

    public void setActiveTour(Tour activeTour);

    public Tour getActiveTour();

    public Image loadTourImage();

    public Image getActiveImage();
}
