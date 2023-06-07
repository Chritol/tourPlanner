package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.*;

public class TourDataDisplayViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final Logger logger;

    private StringProperty nameText = new SimpleStringProperty();
    private StringProperty descriptionText = new SimpleStringProperty();
    private StringProperty fromText = new SimpleStringProperty();
    private StringProperty toText = new SimpleStringProperty();
    private StringProperty transportationText = new SimpleStringProperty();
    private StringProperty hillinessText = new SimpleStringProperty();

    private StringProperty distanceText = new SimpleStringProperty();
    private StringProperty estimatedTimeText = new SimpleStringProperty();

    private BooleanProperty submitButtonIsActive = new SimpleBooleanProperty(false);


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

        distanceText.set("-");
        estimatedTimeText.set("-");

        submitButtonIsActive.set(false);
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.OPEN_TOUR_ACTION, this::onLoadedTourAction);
    }

    public void onLoadedTourAction() {
        logger.info("LOADED_TOUR_ACTION event received, getting active Tour and updating display");

        Tour activeTour = this.tourService.getActiveTour();

        if (activeTour == null) {
            logger.warn("No active tour");
            setDefaultText();
        }else {
            nameText.set(activeTour.getName());
            descriptionText.set(activeTour.getDescription());
            fromText.set(activeTour.getFrom());
            toText.set(activeTour.getTo());
            transportationText.set(activeTour.getTransportation().toString());
            hillinessText.set(activeTour.getHilliness().toString());
            distanceText.set(activeTour.getDistance()+" km");
            estimatedTimeText.set(activeTour.getEstimatedTime()+" minutes");

        }
    }

    public void handleEditButtonClicked() {
        logger.info("edit button in display view clicked, publishing event EDIT_TOUR_ACTION");

        eventAggregator.publish(Event.EDIT_TOUR_ACTION);
    }

    public void checkActiveTour() {
        // Replace this with your actual implementation of TourService
        if (tourService.getActiveTour() != null) {
            submitButtonIsActive.set(true);
        } else {
            submitButtonIsActive.set(false);
        }
    }




    //setters and getters
    public String getNameText() {
        return nameText.get();
    }

    public StringProperty nameTextProperty() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText.set(nameText);
    }

    public String getDescriptionText() {
        return descriptionText.get();
    }

    public StringProperty descriptionTextProperty() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText.set(descriptionText);
    }

    public String getFromText() {
        return fromText.get();
    }

    public StringProperty fromTextProperty() {
        return fromText;
    }

    public void setFromText(String fromText) {
        this.fromText.set(fromText);
    }

    public String getToText() {
        return toText.get();
    }

    public StringProperty toTextProperty() {
        return toText;
    }

    public void setToText(String toText) {
        this.toText.set(toText);
    }

    public String getTransportationText() {
        return transportationText.get();
    }

    public StringProperty transportationTextProperty() {
        return transportationText;
    }

    public void setTransportationText(String transportationText) {
        this.transportationText.set(transportationText);
    }

    public String getHillinessText() {
        return hillinessText.get();
    }

    public StringProperty hillinessTextProperty() {
        return hillinessText;
    }

    public void setHillinessText(String hillinessText) {
        this.hillinessText.set(hillinessText);
    }

    public String getDistanceText() {
        return distanceText.get();
    }

    public StringProperty distanceTextProperty() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText.set(distanceText);
    }

    public String getEstimatedTimeText() {
        return estimatedTimeText.get();
    }

    public StringProperty estimatedTimeTextProperty() {
        return estimatedTimeText;
    }

    public void setEstimatedTimeText(String estimatedTimeText) {
        this.estimatedTimeText.set(estimatedTimeText);
    }

    public boolean isSubmitButtonIsActive() {
        return submitButtonIsActive.get();
    }

    public BooleanProperty submitButtonIsActiveProperty() {
        return submitButtonIsActive;
    }

    public void setSubmitButtonIsActive(boolean submitButtonIsActive) {
        this.submitButtonIsActive.set(submitButtonIsActive);
    }
}
