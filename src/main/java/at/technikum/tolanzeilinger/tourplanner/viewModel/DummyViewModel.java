package at.technikum.tolanzeilinger.tourplanner.viewModel;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;

public class DummyViewModel implements ViewModel {

    private final EventAggregator eventAggregator;
    private final Logger logger;

    public DummyViewModel(
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
        //Set calculated values for properties here
    }

    @Override
    public void initializeEventListeners() {
        //Add event listeners here
    }




}

