package at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.RouteItem;
import at.technikum.tolanzeilinger.tourplanner.service.TourService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.IRouteService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;

public class TourMapViewModel implements ViewModel {
    private final EventAggregator eventAggregator;

    private final ObjectProperty<Image> image = new SimpleObjectProperty<Image>();
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
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.ROUTE_LOADED, this::updateImage);
    }

    @Override
    public void initializeView() { }


    private void updateImage() {
        this.tourService.getActiveImage();
    }

    public Image getRouteImage() {
        return null;
    }
}
