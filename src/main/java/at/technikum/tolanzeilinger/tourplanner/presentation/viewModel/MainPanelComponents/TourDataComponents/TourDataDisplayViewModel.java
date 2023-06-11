package at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.ViewModel;
import javafx.beans.property.*;
import javafx.scene.image.Image;

public class TourDataDisplayViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final Logger logger;

    private final StringProperty nameText = new SimpleStringProperty();
    private final StringProperty descriptionText = new SimpleStringProperty();
    private final StringProperty fromText = new SimpleStringProperty();
    private final StringProperty toText = new SimpleStringProperty();
    private final StringProperty transportationText = new SimpleStringProperty();
    private final StringProperty hillinessText = new SimpleStringProperty();
    private final StringProperty popularityText = new SimpleStringProperty();
    private final StringProperty childFriendlinessText = new SimpleStringProperty();
    private final StringProperty distanceText = new SimpleStringProperty();
    private final StringProperty estimatedTimeText = new SimpleStringProperty();

    private final BooleanProperty submitButtonIsActive = new SimpleBooleanProperty(false);

    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    private final TourService tourService;

    public TourDataDisplayViewModel(
            EventAggregator eventAggregator,
            Logger logger,
            TourService tourService) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;
        this.tourService = tourService;

        initializeView();
        initializeEventListeners();
    }

    @Override
    public void initializeView() {
        setDefaultText();
    }

    public void setDefaultText() {
        nameText.set("Select a tour");
        descriptionText.set("There is no tour selected. So there is nothing to see here.\nTry selecting a tour from the list on the left ;)");
        fromText.set("-");
        toText.set("-");
        transportationText.set("-");
        hillinessText.set("-");
        childFriendlinessText.set("-");
        popularityText.set("-");
        distanceText.set("-");
        estimatedTimeText.set("-");

        submitButtonIsActive.set(false);
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.TOUR_CHANGED, this::onLoadedTourAction);
        eventAggregator.addSubscriber(Event.OPEN_TOUR_ACTION, this::onLoadedTourAction);
        eventAggregator.addSubscriber(Event.TOUR_UPDATED, this::onLoadedTourAction);
        eventAggregator.addSubscriber(Event.TOUR_CREATED, this::onLoadedTourAction);
        eventAggregator.addSubscriber(Event.TOUR_DELETED, this::onLoadedTourAction);
    }

    public void onLoadedTourAction() {
        logger.info("event received, getting active Tour and updating display");

        Tour activeTour = tourService.getActiveTour();

        if (activeTour == null) {
            logger.warn("No active tour");
            setDefaultText();
        } else {
            nameText.set(activeTour.getName());
            descriptionText.set(activeTour.getDescription());
            fromText.set(activeTour.getFrom());
            toText.set(activeTour.getTo());
            transportationText.set(activeTour.getTransportation().toString());
            hillinessText.set(activeTour.getHilliness().toString());
            distanceText.set(activeTour.getDistance()+" km");
            estimatedTimeText.set(activeTour.getEstimatedTime()+" minutes");
            popularityText.set(activeTour.getPopularity().getTextValue());
            childFriendlinessText.set(activeTour.getChildFriendliness().getTextValue());

            imageProperty.set(tourService.getActiveImage());
        }
    }

    public void handleEditButtonClicked() {
        logger.info("edit button in display view clicked, publishing event EDIT_TOUR_ACTION");

        eventAggregator.publish(Event.EDIT_TOUR_ACTION);
    }

    public StringProperty nameTextProperty() {
        return nameText;
    }

    public StringProperty descriptionTextProperty() {
        return descriptionText;
    }

    public StringProperty fromTextProperty() {
        return fromText;
    }

    public StringProperty toTextProperty() {
        return toText;
    }

    public StringProperty transportationTextProperty() {
        return transportationText;
    }

    public StringProperty hillinessTextProperty() {
        return hillinessText;
    }

    public StringProperty distanceTextProperty() {
        return distanceText;
    }

    public StringProperty estimatedTimeTextProperty() {
        return estimatedTimeText;
    }

    public BooleanProperty submitButtonIsActiveProperty() {
        return submitButtonIsActive;
    }

    public ObjectProperty<Image> imagePropertyProperty() {
        return imageProperty;
    }

    public StringProperty popularityTextProperty() {
        return popularityText;
    }

    public StringProperty childFriendlinessTextProperty() {
        return childFriendlinessText;
    }
}
