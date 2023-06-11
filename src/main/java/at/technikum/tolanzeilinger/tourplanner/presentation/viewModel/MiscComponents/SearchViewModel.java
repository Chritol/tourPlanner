package at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.ViewModel;


public class SearchViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final Logger logger;
    private final TourService tourService;

    public SearchViewModel(
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
        // Set calculated values for properties here
    }

    @Override
    public void initializeEventListeners() {
        // Add event listeners here
    }

    public void search(String searchString) {
        // Perform search and update the view or print the current string
        tourService.setFullTextSearchString(searchString);

        eventAggregator.publish(Event.SEARCH_ACTION);
    }
}
