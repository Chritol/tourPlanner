package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.TourServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class TourMapViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private StringProperty nameText = new SimpleStringProperty();

    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private final TourService tourService;
    private final Logger logger;

    public TourMapViewModel(
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
        setDefaultText();
    }

    public void setDefaultText() {
        nameText.set("Select a tour");
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.TOUR_CHANGED, this::onLoadedTourAction);
        eventAggregator.addSubscriber(Event.OPEN_TOUR_ACTION, this::onLoadedTourAction);
    }

    public void onLoadedTourAction() {
        logger.info("event received, getting active Tour and updating display");

        Tour activeTour = tourService.getActiveTour();

        if (activeTour == null) {
            logger.warn("No active tour");
            setDefaultText();
        } else {
            nameText.set(activeTour.getName());

            imageProperty.set(tourService.getActiveImage());
        }
    }

    public String getNameText() {
        return nameText.get();
    }

    public StringProperty nameTextProperty() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText.set(nameText);
    }

    public Image getImageProperty() {
        return imageProperty.get();
    }

    public ObjectProperty<Image> imagePropertyProperty() {
        return imageProperty;
    }

    public void setImageProperty(Image imageProperty) {
        this.imageProperty.set(imageProperty);
    }
}
