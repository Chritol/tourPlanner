package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourConverter;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.RouteItem;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Tour;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.MapquestService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;

// just for talking to the Repository
public class TourServiceImpl implements TourService {
    private final Logger logger;
    private final EventAggregator eventAggregator;

    // Repositories
    private final TourRepository tourRepository;

    // Services
    private final MapquestUrlBuilderService mapquestUrlBuilderService;
    private final MapquestService mapquestService;

    // Miscellaneous
    private Tour activeTour;

    public TourServiceImpl(Logger logger, EventAggregator eventAggregator, TourRepository tourRepository, MapquestService mapquestService, MapquestUrlBuilderService mapquestUrlBuilderService) {
        this.logger = logger;
        this.eventAggregator = eventAggregator;

        // Repositories
        this.tourRepository = tourRepository;

        // Services
        this.mapquestService = mapquestService;
        this.mapquestUrlBuilderService = mapquestUrlBuilderService;

        // Miscellaneous
        this.activeTour = TourConverter.toTour(tourRepository.findFirst());
        eventAggregator.publish(Event.TOUR_LOADED);
    }

    public void addTour(Tour tour) {
        var id = tourRepository.create(TourConverter.toTourDaoModel(tour));
        logger.info("NEW TOUR ID:"+id);
    }

    public void setActiveTour(Tour activeTour) {
        this.activeTour = activeTour;
    }

    public Tour getActiveTour() {
        return activeTour;
    }

    public Image loadTourImage() {
        try {
            RouteItem routeFromUrl = mapquestService.loadRouteFromUrl( this.mapquestUrlBuilderService.buildDirectionsUrl(this.activeTour), null);
            Image image = mapquestService.getRouteImage(this.mapquestUrlBuilderService.buildMapImageUrl(this.activeTour), routeFromUrl.getSessionId());
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