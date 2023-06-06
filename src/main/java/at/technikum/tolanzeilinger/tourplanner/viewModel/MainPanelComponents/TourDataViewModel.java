package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Hilltype;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Transportation;
import at.technikum.tolanzeilinger.tourplanner.service.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourDataViewModel implements ViewModel {
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

    public TourDataViewModel(
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
    public void initializeView() {
        transportationOptionsProperty.set(FXCollections.observableArrayList(Transportation.values()));
        hillinessOptionsProperty.set(FXCollections.observableArrayList(Hilltype.values()));
    }

    @Override
    public void initializeEventListeners() {

    }


    // Getters and setters for the properties go here
    public SimpleStringProperty getNameProperty() {
        return nameProperty;
    }

    public SimpleStringProperty namePropertyProperty() {
        return nameProperty;
    }

    public void setNameProperty(String nameProperty) {
        this.nameProperty.set(nameProperty);
    }

    public SimpleStringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    public SimpleStringProperty descriptionPropertyProperty() {
        return descriptionProperty;
    }

    public void setDescriptionProperty(String descriptionProperty) {
        this.descriptionProperty.set(descriptionProperty);
    }

    public SimpleStringProperty getFromProperty() {
        return fromProperty;
    }

    public SimpleStringProperty fromPropertyProperty() {
        return fromProperty;
    }

    public void setFromProperty(String fromProperty) {
        this.fromProperty.set(fromProperty);
    }

    public SimpleStringProperty getToProperty() {
        return toProperty;
    }

    public SimpleStringProperty toPropertyProperty() {
        return toProperty;
    }

    public void setToProperty(String toProperty) {
        this.toProperty.set(toProperty);
    }

    public SimpleObjectProperty<Transportation> getTransportationProperty() {
        return transportationProperty;
    }

    public SimpleObjectProperty<Transportation> transportationPropertyProperty() {
        return transportationProperty;
    }

    public void setTransportationProperty(Transportation transportationProperty) {
        this.transportationProperty.set(transportationProperty);
    }

    public SimpleObjectProperty<Hilltype> getHillinessProperty() {
        return hillinessProperty;
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

    public SimpleObjectProperty<ObservableList<Hilltype>> getHillinessOptionsProperty() {
        return (SimpleObjectProperty<ObservableList<Hilltype>>) hillinessOptionsProperty.get();
    }

    public SimpleObjectProperty<ObservableList<Hilltype>> hillinessOptionsPropertyProperty() {
        return hillinessOptionsProperty;
    }

    public void setHillinessOptionsProperty(ObservableList<Hilltype> hillinessOptionsProperty) {
        this.hillinessOptionsProperty.set(hillinessOptionsProperty);
    }



    // Other methods and properties go here
}
