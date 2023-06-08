package at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TourListViewModel implements ViewModel {

    private final EventAggregator eventAggregator;
    private final Logger logger;
    private final TourService tourService;
    private List<Tour> allTours;

    private ListProperty<String> tourNames = new SimpleListProperty<String>();


    public TourListViewModel(
            EventAggregator eventAggregator,
            Logger logger,
            TourService tourService
    ) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;
        this.tourService = tourService;

        initializeView();
        initializeEventListeners();
    }

    @Override
    public void initializeView() {
        update();
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.TOUR_CREATED, this::update);
        eventAggregator.addSubscriber(Event.TOUR_DELETED, this::update);
        eventAggregator.addSubscriber(Event.TOUR_UPDATED, this::update);
    }

    private void update() {
        this.allTours = tourService.getTours();
        ObservableList<String> allTourNames = FXCollections.observableArrayList();

        for (Tour tour : allTours) {
            allTourNames.add(tour.getName());
        }

        tourNames.set(allTourNames);
    }

    public void selectTour(String selectedName) {
        logger.info("Selected tour: " + selectedName);

        long id = -1;

        for (Tour tour : allTours) {
            String name = tour.getName();
            if (name.equals(selectedName)) {
                id = tour.getId();
            }
        }

        tourService.setActiveTourIndex(id);

        eventAggregator.publish(Event.SELECT_NEW_ACTIVE_TOUR_ACTION);
        eventAggregator.publish(Event.OPEN_TOUR_ACTION);
    }

    public ObservableList<String> getTourNames() {
        return tourNames.get();
    }

    public ListProperty<String> tourNamesProperty() {
        return tourNames;
    }

    public void setTourNames(ObservableList<String> tourNames) {
        this.tourNames.set(tourNames);
    }
}

