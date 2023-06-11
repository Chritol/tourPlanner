package at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TourDataPaneSwitcherViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final Logger logger;

    private final BooleanProperty displayVisible= new SimpleBooleanProperty(false);
    private final BooleanProperty createVisible= new SimpleBooleanProperty(false);
    private final BooleanProperty editVisible = new SimpleBooleanProperty(false);

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
        switchToDisplay();
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.OPEN_TOUR_ACTION, this::switchToDisplay);
        eventAggregator.addSubscriber(Event.EXIT_FORM_TOUR_ACTION, this::switchToDisplay);
        eventAggregator.addSubscriber(Event.TOUR_CHANGED, this::switchToDisplay);


        eventAggregator.addSubscriber(Event.EDIT_TOUR_ACTION, this::switchToEdit);
        eventAggregator.addSubscriber(Event.NEW_TOUR_ACTION, this::switchToCreate);
    }

    public void switchToDisplay() {
        logger.info("Switching to display view");

        displayVisible.set(true);
        createVisible.set(false);
        editVisible.set(false);
    }

    public void switchToEdit() {
        logger.info("Switching to edit view");

        displayVisible.set(false);
        createVisible.set(false);
        editVisible.set(true);
    }

    public void switchToCreate() {
        logger.info("Switching to create view");

        displayVisible.set(false);
        createVisible.set(true);
        editVisible.set(false);
    }

    public BooleanProperty displayVisibleProperty() {
        return displayVisible;
    }

    public BooleanProperty createVisibleProperty() {
        return createVisible;
    }

    public BooleanProperty editVisibleProperty() {
        return editVisible;
    }
}
