package at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;

public class TourLogsActionButtonsViewModel implements ViewModel {

    private final EventAggregator eventAggregator;
    private final Logger logger;

    public TourLogsActionButtonsViewModel(
            EventAggregator eventAggregator,
            Logger logger
    ) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;

        initializeView();
        initializeEventListeners();
    }

    @Override
    public void initializeView() {

    }

    @Override
    public void initializeEventListeners() {

    }

    public void add() {
        System.out.println("add log");

        eventAggregator.publish(Event.NEW_TOUR_ACTION);
    }

    public void remove() {
        System.out.println("remove log");

        eventAggregator.publish(Event.REMOVE_TOUR_ACTION);
    }

    public void manual() {
        System.out.println("manual log");
    }


}

