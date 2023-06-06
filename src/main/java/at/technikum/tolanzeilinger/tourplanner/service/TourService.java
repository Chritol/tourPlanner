package at.technikum.tolanzeilinger.tourplanner.service;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.RouteItem;
import at.technikum.tolanzeilinger.tourplanner.model.repositories.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import at.technikum.tolanzeilinger.tourplanner.service.helperServices.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.RouteService;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;

// just for talking to the Repository
public class TourService {
    private final Logger logger;
    private final EventAggregator eventAggregator;

    // Repositories
    private final TourRepository tourRepository;

    // Services
    private final MapquestUrlBuilderService mapquestUrlBuilderService;
    private final RouteService routeService;

    // Miscellaneous
    private Tour activeTour;

    public TourService(Logger logger, EventAggregator eventAggregator, TourRepository tourRepository, RouteService routeService, MapquestUrlBuilderService mapquestUrlBuilderService) {
        this.logger = logger;
        this.eventAggregator = eventAggregator;

        // Repositories
        this.tourRepository = tourRepository;

        // Services
        this.routeService = routeService;
        this.mapquestUrlBuilderService = mapquestUrlBuilderService;

        // Miscellaneous
        this.activeTour = tourRepository.findFirst();
    }

    public void addNewTour(Tour tour) {
        tourRepository.add(tour);
    }

    public void setActiveTour(Tour activeTour) {
        this.activeTour = activeTour;
    }

    public Tour getActiveTour() {
        return activeTour;
    }

    public Image loadTourImage() {
        try {
            RouteItem routeFromUrl = routeService.loadRouteFromUrl( this.mapquestUrlBuilderService.buildDirectionsUrl(this.activeTour), null);
            Image image = routeService.getRouteImage(this.mapquestUrlBuilderService.buildMapUrl(this.activeTour), routeFromUrl.getSessionId());
            return image;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Image getActiveImage() {
        // TODO: implement To get image of active tour
        return null;
    }
}
