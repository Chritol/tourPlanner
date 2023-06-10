package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.constants.StylingConstants;
import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.enums.Transportation;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Border;

import static at.technikum.tolanzeilinger.tourplanner.util.StringUtilities.*;
import static at.technikum.tolanzeilinger.tourplanner.util.StringUtilities.isTextTooLong;

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
    private SimpleObjectProperty<HillType> hillinessProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<ObservableList<Transportation>> transportationOptionsProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<ObservableList<HillType>> hillinessOptionsProperty = new SimpleObjectProperty<>();


    ObjectProperty<Border> nameBorderProperty = new SimpleObjectProperty<>();
    ObjectProperty<Border> descriptionBorderProperty = new SimpleObjectProperty<>();
    ObjectProperty<Border> fromBorderProperty = new SimpleObjectProperty<>();
    ObjectProperty<Border> toBorderProperty = new SimpleObjectProperty<>();
    ObjectProperty<Border> transportationBorderProperty = new SimpleObjectProperty<>();
    ObjectProperty<Border> hillinessBorderProperty = new SimpleObjectProperty<>();

    private BooleanProperty submitButtonIsActive = new SimpleBooleanProperty(false);

    public TourDataCreateViewModel(
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
        hillinessOptionsProperty.set(FXCollections.observableArrayList(HillType.values()));

        setDefaultText();
    }

    public void setDefaultText() {
        nameProperty.set(null);
        descriptionProperty.set(null);
        fromProperty.set(null);
        toProperty.set(null);

        transportationProperty.set(null);
        hillinessProperty.set(null);

        submitButtonIsActive.set(false);

        resetBorderStyles();
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

    public SimpleObjectProperty<HillType> getHillinessProperty() {
        return hillinessProperty;
    }

    public void setHillinessProperty(HillType hilliness) {
        this.hillinessProperty.set(hilliness);
    }

    public SimpleObjectProperty<ObservableList<Transportation>> getTransportationOptionsProperty() {
        return transportationOptionsProperty;
    }

    public void setTransportationOptionsProperty(ObservableList<Transportation> transportationOptions) {
        this.transportationOptionsProperty.set(transportationOptions);
    }

    public SimpleObjectProperty<ObservableList<HillType>> getHillinessOptionsProperty() {
        return hillinessOptionsProperty;
    }

    public void setHillinessOptionsProperty(ObservableList<HillType> hillinessOptions) {
        this.hillinessOptionsProperty.set(hillinessOptions);
    }


    public SimpleStringProperty namePropertyProperty() {
        return nameProperty;
    }

    public SimpleStringProperty descriptionPropertyProperty() {
        return descriptionProperty;
    }

    public SimpleStringProperty fromPropertyProperty() {
        return fromProperty;
    }

    public SimpleStringProperty toPropertyProperty() {
        return toProperty;
    }

    public SimpleObjectProperty<Transportation> transportationPropertyProperty() {
        return transportationProperty;
    }

    public SimpleObjectProperty<HillType> hillinessPropertyProperty() {
        return hillinessProperty;
    }

    public SimpleObjectProperty<ObservableList<Transportation>> transportationOptionsPropertyProperty() {
        return transportationOptionsProperty;
    }

    public SimpleObjectProperty<ObservableList<HillType>> hillinessOptionsPropertyProperty() {
        return hillinessOptionsProperty;
    }

    public Border getNameBorderProperty() {
        return nameBorderProperty.get();
    }

    public ObjectProperty<Border> nameBorderPropertyProperty() {
        return nameBorderProperty;
    }

    public void setNameBorderProperty(Border nameBorderProperty) {
        this.nameBorderProperty.set(nameBorderProperty);
    }

    public Border getDescriptionBorderProperty() {
        return descriptionBorderProperty.get();
    }

    public ObjectProperty<Border> descriptionBorderPropertyProperty() {
        return descriptionBorderProperty;
    }

    public void setDescriptionBorderProperty(Border descriptionBorderProperty) {
        this.descriptionBorderProperty.set(descriptionBorderProperty);
    }

    public Border getFromBorderProperty() {
        return fromBorderProperty.get();
    }

    public ObjectProperty<Border> fromBorderPropertyProperty() {
        return fromBorderProperty;
    }

    public void setFromBorderProperty(Border fromBorderProperty) {
        this.fromBorderProperty.set(fromBorderProperty);
    }

    public Border getToBorderProperty() {
        return toBorderProperty.get();
    }

    public ObjectProperty<Border> toBorderPropertyProperty() {
        return toBorderProperty;
    }

    public void setToBorderProperty(Border toBorderProperty) {
        this.toBorderProperty.set(toBorderProperty);
    }

    public Border getTransportationBorderProperty() {
        return transportationBorderProperty.get();
    }

    public ObjectProperty<Border> transportationBorderPropertyProperty() {
        return transportationBorderProperty;
    }

    public void setTransportationBorderProperty(Border transportationBorderProperty) {
        this.transportationBorderProperty.set(transportationBorderProperty);
    }

    public Border getHillinessBorderProperty() {
        return hillinessBorderProperty.get();
    }

    public ObjectProperty<Border> hillinessBorderPropertyProperty() {
        return hillinessBorderProperty;
    }

    public void setHillinessBorderProperty(Border hillinessBorderProperty) {
        this.hillinessBorderProperty.set(hillinessBorderProperty);
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
        resetBorderStyles();

        cleanInputs();

        if (!isAnyPropertyInvalid()) {
            // All properties are not null
            logger.info("If you see this pray to god!");

            addTour();

            setDefaultText();

            eventAggregator.publish(Event.EXIT_FORM_TOUR_ACTION);
        } else {
            // At least one property is null
            logger.warn("At least one property is null");

            markInvalidFields();
        }
    }


    private void cleanInputs() {
        nameProperty.set(clearTrailingWhitespaces(nameProperty.get()));
        descriptionProperty.set(clearTrailingWhitespaces(descriptionProperty.get()));
        fromProperty.set(clearTrailingWhitespaces(fromProperty.get()));
        toProperty.set(clearTrailingWhitespaces(toProperty.get()));
    }

    private boolean isAnyPropertyInvalid() {
        return isNullOrWhitespace(nameProperty.get()) ||
                isNullOrWhitespace(descriptionProperty.get()) ||
                isNullOrWhitespace(fromProperty.get()) ||
                isNullOrWhitespace(toProperty.get()) ||
                isTextTooLong(nameProperty.get(), 50) ||
                isTextTooLong(descriptionProperty.get(), 100) ||
                isTextTooLong(fromProperty.get(), 100) ||
                isTextTooLong(toProperty.get(), 100) ||
                transportationProperty.get() == null ||
                hillinessProperty.get() == null;
    }

    private void markInvalidFields() {
        if (isNullOrWhitespace(nameProperty.get()) || isTextTooLong(nameProperty.get(), 50)) {
            nameBorderProperty.set(StylingConstants.ERROR_BORDER);
        }
        if (isNullOrWhitespace(descriptionProperty.get()) || isTextTooLong(descriptionProperty.get(), 100)) {
            descriptionBorderProperty.set(StylingConstants.ERROR_BORDER);
        }
        if (isNullOrWhitespace(fromProperty.get()) || isTextTooLong(fromProperty.get(), 100)) {
            fromBorderProperty.set(StylingConstants.ERROR_BORDER);
        }
        if (isNullOrWhitespace(toProperty.get()) || isTextTooLong(toProperty.get(), 100)) {
            toBorderProperty.set(StylingConstants.ERROR_BORDER);
        }
        if (transportationProperty.get() == null) {
            transportationBorderProperty.set(StylingConstants.ERROR_BORDER);
        }
        if (hillinessProperty.get() == null) {
            hillinessBorderProperty.set(StylingConstants.ERROR_BORDER);
        }
    }

    private void resetBorderStyles() {
        nameBorderProperty.set(StylingConstants.NORMAL_BORDER);
        descriptionBorderProperty.set(StylingConstants.NORMAL_BORDER);
        fromBorderProperty.set(StylingConstants.NORMAL_BORDER);
        toBorderProperty.set(StylingConstants.NORMAL_BORDER);
        transportationBorderProperty.set(StylingConstants.NORMAL_BORDER);
        hillinessBorderProperty.set(StylingConstants.NORMAL_BORDER);
    }


    public void handleClose() {
        logger.info("handling Close Button click, sending out event EXIT_FORM_TOUR_ACTION");

        setDefaultText();

        eventAggregator.publish(Event.EXIT_FORM_TOUR_ACTION);
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
        tour.setHilliness(hillinessProperty.get());

        tour.setDistance(0);
        tour.setEstimatedTime(0);

        tourService.addTour(tour);
    }
}
