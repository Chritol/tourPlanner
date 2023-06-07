package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;

public class TourMiscViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final TourService tourService;
    private final Logger logger;

    public TourMiscViewModel(
            EventAggregator eventAggregator,
            TourService tourService,
            Logger logger
    ) {
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;
        this.logger = logger;

        initializeView();
        initializeEventListeners();
    }

    @Override
    public void initializeEventListeners() {
    }

    @Override
    public void initializeView() {

    }




}
