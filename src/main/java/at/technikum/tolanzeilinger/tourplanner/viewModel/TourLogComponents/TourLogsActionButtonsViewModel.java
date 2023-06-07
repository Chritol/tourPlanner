package at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
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
        logger.info("Publishing add log action");

        eventAggregator.publish(Event.NEW_LOG_ACTION);
    }

    public void remove() {
        logger.info("Publishing remove log action");

        eventAggregator.publish(Event.REMOVE_LOG_ACTION);
    }

    public void manual() {
        logger.info("Publishing load misc tab action");

        eventAggregator.publish(Event.OPEN_MISC_ACTION);
    }


}

