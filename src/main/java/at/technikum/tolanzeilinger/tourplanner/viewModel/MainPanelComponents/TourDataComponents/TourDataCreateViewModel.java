package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Hilltype;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Transportation;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.TourServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourDataCreateViewModel implements ViewModel {
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

    public TourDataCreateViewModel(
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

    public void setNameProperty(String name) {
        this.nameProperty.set(name);
    }

    public SimpleStringProperty getDescriptionProperty() {
        return descriptionProperty;
    }

    public void setDescriptionProperty(String description) {
        this.descriptionProperty.set(description);
    }

    public SimpleStringProperty getFromProperty() {
        return fromProperty;
    }

    public void setFromProperty(String from) {
        this.fromProperty.set(from);
    }

    public SimpleStringProperty getToProperty() {
        return toProperty;
    }

    public void setToProperty(String to) {
        this.toProperty.set(to);
    }

    public SimpleObjectProperty<Transportation> getTransportationProperty() {
        return transportationProperty;
    }

    public void setTransportationProperty(Transportation transportation) {
        this.transportationProperty.set(transportation);
    }

    public SimpleObjectProperty<Hilltype> getHillinessProperty() {
        return hillinessProperty;
    }

    public void setHillinessProperty(Hilltype hilliness) {
        this.hillinessProperty.set(hilliness);
    }

    public SimpleObjectProperty<ObservableList<Transportation>> getTransportationOptionsProperty() {
        return transportationOptionsProperty;
    }

    public void setTransportationOptionsProperty(ObservableList<Transportation> transportationOptions) {
        this.transportationOptionsProperty.set(transportationOptions);
    }

    public SimpleObjectProperty<ObservableList<Hilltype>> getHillinessOptionsProperty() {
        return hillinessOptionsProperty;
    }

    public void setHillinessOptionsProperty(ObservableList<Hilltype> hillinessOptions) {
        this.hillinessOptionsProperty.set(hillinessOptions);
    }

    public void submit() {
        //TODO: Handle the submission of the data
    }


    // Other methods and properties go here
    public void addTour() {
        Tour tour = new Tour(
                nameProperty.get(),
                descriptionProperty.get(),
                fromProperty.get(),
                toProperty.get()
        );
        tour.setTransportation(transportationProperty.get());

        // TODO - hier routeservice call - service war down als ich das grad geschrieben habe
        // routeService.

        tour.setDistance(500);
        tour.setEstimatedTime(500);

        tour.setHilliness(hillinessProperty.get());

        tourService.addTour(tour);
    }
}
