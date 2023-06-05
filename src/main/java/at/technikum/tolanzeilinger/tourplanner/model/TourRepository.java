package at.technikum.tolanzeilinger.tourplanner.model;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.model.tourStuff.Tour;

import java.util.ArrayList;
import java.util.List;

public class TourRepository {
    private final List<Tour> tours;
    private final EventAggregator eventAggregator;

    public TourRepository(EventAggregator eventAggregator) {
        this.eventAggregator = eventAggregator;

        tours = new ArrayList<>();
    }

    public void add(Tour tour) {
        if(tour != null && !tours.contains(tour))
            tours.add(tour);

        eventAggregator.publish(Event.CREATE_WORD);
    }

    public void remove(Tour tour) {
        if(tour != null)
            tours.remove(tour);

        eventAggregator.publish(Event.DELETE_WORD);
    }

    public List<Tour> findAll() {
        return tours;
    }
}
