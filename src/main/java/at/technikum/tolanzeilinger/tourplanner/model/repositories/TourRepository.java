package at.technikum.tolanzeilinger.tourplanner.model.repositories;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TourRepository implements Repository<Tour> {
    private final List<Tour> tours;
    private final EventAggregator eventAggregator;

    public TourRepository(EventAggregator eventAggregator) {
        this.eventAggregator = eventAggregator;

        tours = new ArrayList<Tour>();
    }

    @Override
    public void add(Tour tour) {
        if(tour != null && !tours.contains(tour))
            tours.add(tour);

        eventAggregator.publish(Event.ROUTE_CREATED);
    }

    @Override
    public void remove(Tour word) {
        if(word != null)
            tours.remove(word);

        eventAggregator.publish(Event.ROUTE_DELETED);
    }

    @Override
    public List<Tour> findAll() {
        return tours;
    }
    public List<Tour> findAll(List<Tour> tours, String pattern) {
        List<Tour> matchingTours = new ArrayList<>();

        Pattern regexPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        for (Tour tour : tours) {
            Matcher matcher = regexPattern.matcher(tour.getName());
            if (matcher.find()) {
                matchingTours.add(tour);
            }
        }

        return matchingTours;
    }

    @Override
    public Tour findFirst() {
        eventAggregator.publish(Event.ROUTE_LOADED);

        if (tours.isEmpty()) {
            return null; // Return null if the list is empty
        }

        return tours.get(0);
    }
    public Tour findFirst(String pattern) {
        eventAggregator.publish(Event.ROUTE_LOADED);

        Pattern regexPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        for (Tour tour : tours) {
            Matcher matcher = regexPattern.matcher(tour.getName());
            if (matcher.find()) {
                return tour;
            }
        }

        return null; // Return null if no matching tour is found
    }




}
