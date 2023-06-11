package at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourMiscViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final TourService tourService;
    private final Logger logger;
    private final StringProperty nameText = new SimpleStringProperty();

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
        eventAggregator.addSubscriber(Event.TOUR_CHANGED, this::onLoadedTourAction);
        eventAggregator.addSubscriber(Event.OPEN_TOUR_ACTION, this::onLoadedTourAction);
    }

        @Override
    public void initializeView() {
        setDefaultText();
    }

    public void setDefaultText() {
        nameText.set("Select a tour");
    }

    public void onLoadedTourAction() {
        logger.info("event received, getting active Tour and updating display");

        Tour activeTour = tourService.getActiveTour();

        if (activeTour == null) {
            logger.warn("No active tour");
            setDefaultText();
        } else {
            nameText.set(activeTour.getName());
        }
    }

    public StringProperty nameTextProperty() {
        return nameText;
    }
}
