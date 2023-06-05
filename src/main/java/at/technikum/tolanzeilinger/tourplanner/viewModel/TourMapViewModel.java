package at.technikum.tolanzeilinger.tourplanner.viewModel;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.model.RouteItem;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.IRouteService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;

public class TourMapViewModel {
    private final EventAggregator eventAggregator;
    private final IRouteService routeService;

    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();

    public TourMapViewModel(
            EventAggregator eventAggregator,
            IRouteService routeService
    ) {
        this.eventAggregator = eventAggregator;
        this.routeService = routeService;

        initializeView();
        initializeEventListeners();
    }

    private void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.ROUTE_LOADED, this::updateImage);
    }

    private void initializeView() { }

    public Image getRouteImage() {
        try {
            RouteItem asd = routeService.loadRouteFromUrl("https://www.mapquestapi.com/directions/v2/route?key=XSqMMjiT0vjeJtxPj22gTLZ2X2LNiDqj&from=Wien&to=Koeln&unit=K", null);
            Image image = routeService.getRouteImage("https://www.mapquestapi.com/staticmap/v5/map?key=XSqMMjiT0vjeJtxPj22gTLZ2X2LNiDqj&session=", asd.getSessionId());
            return image;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    private void updateImage() {

    }
}
