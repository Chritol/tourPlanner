package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourDataDisplayViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final Logger logger;

    private StringProperty nameText = new SimpleStringProperty();
    private StringProperty descriptionText = new SimpleStringProperty();
    private StringProperty fromText = new SimpleStringProperty();
    private StringProperty toText = new SimpleStringProperty();
    private StringProperty transportationText = new SimpleStringProperty();
    private StringProperty hillinessText = new SimpleStringProperty();

    public TourDataDisplayViewModel(
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
        eventAggregator.addSubscriber(Event.LOADED_TOUR_ACTION, this::onLoadedTourAction);
    }

    public void onLoadedTourAction() {

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
}
