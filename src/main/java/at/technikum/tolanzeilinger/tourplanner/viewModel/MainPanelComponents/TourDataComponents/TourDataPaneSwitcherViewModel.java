package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;

public class TourDataPaneSwitcherViewModel implements ViewModel {

    private final EventAggregator eventAggregator;
    private final Logger logger;

    public TourDataPaneSwitcherViewModel(
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
        eventAggregator.addSubscriber(Event.LOADED_TOUR_ACTION, this::switchToDisplay);
        eventAggregator.addSubscriber(Event.EDIT_TOUR_ACTION, this::switchToEdit);
        eventAggregator.addSubscriber(Event.NEW_TOUR_ACTION, this::switchToCreate);
    }

    public void switchToDisplay() {}

    public void switchToEdit() {}

    public void switchToCreate() {}
}
