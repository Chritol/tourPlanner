package at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.ViewModel;

public class TopButtonsViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final Logger logger;

    public TopButtonsViewModel(
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
        // Set calculated values for properties here
    }

    @Override
    public void initializeEventListeners() {
        // Add event listeners here
    }

    public void handleFileButtonClick() {
        logger.info("File button clicked");
        // Handle file button click logic here

        eventAggregator.publish(Event.OPEN_FILE_DIRECTORY_ACTION);
    }

    public void handleEditButtonClick() {
        logger.info("Edit button clicked");
        // Handle edit button click logic here

        eventAggregator.publish(Event.EDIT_TOUR_ACTION);
    }

    public void handleOptionsButtonClick() {
        logger.info("Options button clicked");
        // Handle options button click logic here

        eventAggregator.publish(Event.OPEN_MISC_ACTION);
    }

    public void handleMapButtonClick() {
        logger.info("Map button clicked");
        // Handle map button click logic here

        eventAggregator.publish(Event.OPEN_MAP_ACTION);
    }

    public void handlePicturesButtonClick() {
        logger.info("Pictures button clicked");
        // Handle pictures button click logic here

        eventAggregator.publish(Event.OPEN_PICTURES_DIRECTORY_ACTION);
    }

    public void handleHelpButtonClick() {
        logger.info("Help button clicked");
        // Handle help button click logic here

        eventAggregator.publish(Event.OPEN_HELP_ACTION);
    }
}
