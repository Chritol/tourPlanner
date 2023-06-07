package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Hilltype;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Transportation;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourDataEditViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final TourService tourService;
    private final Logger logger;


    // Properties go here
    private SimpleStringProperty nameProperty = new SimpleStringProperty();
    private SimpleStringProperty descriptionProperty = new SimpleStringProperty();
    private SimpleStringProperty fromProperty = new SimpleStringProperty();
    private SimpleStringProperty toProperty = new SimpleStringProperty();
    private SimpleObjectProperty<Transportation> transportationProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Hilltype> hillinessProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<ObservableList<Transportation>> transportationOptionsProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<ObservableList<Hilltype>> hillinessOptionsProperty = new SimpleObjectProperty<>();

    private BooleanProperty submitButtonIsActive = new SimpleBooleanProperty(false);


    public TourDataEditViewModel(
            EventAggregator eventAggregator,
            Logger logger,
            TourService tourService
    ) {
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;
        this.logger = logger;

        initializeView();
        initializeEventListeners();
    }


    @Override
    public void initializeView() {
        transportationOptionsProperty.set(FXCollections.observableArrayList(Transportation.values()));
        hillinessOptionsProperty.set(FXCollections.observableArrayList(Hilltype.values()));

        setDefaultText();
    }

    public void setDefaultText() {
        nameProperty.set("No item selected?");
        descriptionProperty.set("Something obviously went wrong here. Try selecting an item from the list on the left.");
        fromProperty.set("-");
        toProperty.set("-");

        transportationProperty.set(null);
        hillinessProperty.set(null);

        submitButtonIsActive.set(false);
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.EDIT_TOUR_ACTION, this::onLoadedTourAction);
    }

    public void onLoadedTourAction() {
        logger.info("EDIT_TOUR_ACTION event received, getting active Tour and updating display");

        Tour activeTour = this.tourService.getActiveTour();

        if (activeTour == null) {
            logger.warn("No active tour");
            setDefaultText();
        }else {
            this.nameProperty.set(activeTour.getName());
            this.descriptionProperty.set(activeTour.getDescription());
            this.fromProperty.set(activeTour.getFrom());
            this.toProperty.set(activeTour.getTo());
            this.transportationProperty.set(activeTour.getTransportation());
            this.hillinessProperty.set(activeTour.getHilliness());
        }



    }


    // Getters and setters for the properties go here


    public String getNameProperty() {
        return nameProperty.get();
    }

    public SimpleStringProperty namePropertyProperty() {
        return nameProperty;
    }

    public void setNameProperty(String nameProperty) {
        this.nameProperty.set(nameProperty);
    }

    public String getDescriptionProperty() {
        return descriptionProperty.get();
    }

    public SimpleStringProperty descriptionPropertyProperty() {
        return descriptionProperty;
    }

    public void setDescriptionProperty(String descriptionProperty) {
        this.descriptionProperty.set(descriptionProperty);
    }

    public String getFromProperty() {
        return fromProperty.get();
    }

    public SimpleStringProperty fromPropertyProperty() {
        return fromProperty;
    }

    public void setFromProperty(String fromProperty) {
        this.fromProperty.set(fromProperty);
    }

    public String getToProperty() {
        return toProperty.get();
    }

    public SimpleStringProperty toPropertyProperty() {
        return toProperty;
    }

    public void setToProperty(String toProperty) {
        this.toProperty.set(toProperty);
    }

    public Transportation getTransportationProperty() {
        return transportationProperty.get();
    }

    public SimpleObjectProperty<Transportation> transportationPropertyProperty() {
        return transportationProperty;
    }

    public void setTransportationProperty(Transportation transportationProperty) {
        this.transportationProperty.set(transportationProperty);
    }

    public Hilltype getHillinessProperty() {
        return hillinessProperty.get();
    }

    public SimpleObjectProperty<Hilltype> hillinessPropertyProperty() {
        return hillinessProperty;
    }

    public void setHillinessProperty(Hilltype hillinessProperty) {
        this.hillinessProperty.set(hillinessProperty);
    }

    public ObservableList<Transportation> getTransportationOptionsProperty() {
        return transportationOptionsProperty.get();
    }

    public SimpleObjectProperty<ObservableList<Transportation>> transportationOptionsPropertyProperty() {
        return transportationOptionsProperty;
    }

    public void setTransportationOptionsProperty(ObservableList<Transportation> transportationOptionsProperty) {
        this.transportationOptionsProperty.set(transportationOptionsProperty);
    }

    public ObservableList<Hilltype> getHillinessOptionsProperty() {
        return hillinessOptionsProperty.get();
    }

    public SimpleObjectProperty<ObservableList<Hilltype>> hillinessOptionsPropertyProperty() {
        return hillinessOptionsProperty;
    }

    public void setHillinessOptionsProperty(ObservableList<Hilltype> hillinessOptionsProperty) {
        this.hillinessOptionsProperty.set(hillinessOptionsProperty);
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

    public void submit() {
        //TODO: Handle the submission of the data
    }

    public void handleClose() {
        logger.info("handling Close Button click, sending out event EXIT_FORM_TOUR_ACTION");

        eventAggregator.publish(Event.EXIT_FORM_TOUR_ACTION);
    }


    // Other methods and properties go here
}
