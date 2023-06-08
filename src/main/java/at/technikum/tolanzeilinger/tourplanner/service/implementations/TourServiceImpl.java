package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourConverter;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.api.models.TourDtoModel;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.service.api.interfaces.MapquestService;
import at.technikum.tolanzeilinger.tourplanner.service.api.interfaces.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.ImageStorageService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

// just for talking to the Repository
public class TourServiceImpl implements TourService {
    private final Logger logger;
    private final EventAggregator eventAggregator;

    // Repositories
    private final TourRepository tourRepository;

    // Services
    private final MapquestUrlBuilderService mapquestUrlBuilderService;
    private final MapquestService mapquestService;

    private final ImageStorageService imageStorageService;

    // Miscellaneous
    private long activeTourIndex = -1;

    private Tour activeTour;

    private Image activeTourImage;

    public TourServiceImpl(Logger logger,
                           EventAggregator eventAggregator,
                           TourRepository tourRepository,
                           MapquestService mapquestService,
                           MapquestUrlBuilderService mapquestUrlBuilderService,
                           ImageStorageService imageStorageService) {
        this.logger = logger;
        this.eventAggregator = eventAggregator;

        // Repositories
        this.tourRepository = tourRepository;

        // Services
        this.mapquestService = mapquestService;
        this.mapquestUrlBuilderService = mapquestUrlBuilderService;
        this.imageStorageService = imageStorageService;

        // Miscellaneous
        eventAggregator.publish(Event.TOUR_LOADED);
    }

    public void addTour(Tour tour) {
        long id = tourRepository.create(TourConverter.toTourDaoModel(tour));

        if(id > 0) {
            fetchImageForRouteAndSave(id, tour.getFrom(), tour.getTo());
            logger.info("NEW TOUR ID:" + id);
        }
    }

    public void setActiveTourIndex(long index) {
        this.activeTourIndex = index;

        setActiveTour();
        setActiveTourImage();
    }

    public long getActiveTourIndex() {
        return activeTourIndex;
    }

    public Tour getActiveTour() {
        return activeTour;
    }

    public Image getActiveImage() {
        return activeTourImage;
    }

    private void setActiveTourImage() {
        try {
            activeTourImage = imageStorageService.loadImage(activeTour.getId());
        } catch (NullPointerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void setActiveTour() {
        if(activeTour == null || activeTourIndex != activeTour.getId()) {
            activeTour = TourConverter.toTour(
                    this.tourRepository.read(this.activeTourIndex)
            );
        }
    }

    private void fetchImageForRouteAndSave(long id, String from, String to) {
        try {
            TourDtoModel routeFromUrl = mapquestService.fetchMapquestRoute(mapquestUrlBuilderService.buildDirectionsUrl(from, to));
            InputStream imageInputStream = mapquestService.fetchMapquestImage(mapquestUrlBuilderService.buildMapImageUrl(routeFromUrl.getSessionId()));

            boolean success = imageStorageService.saveImage(imageInputStream, id);
            if(!success) {
                logger.error("There was an issue saving the image for tour with id: " + id);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } catch (NullPointerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Tour> getTours() {
        // Get all tours
        List<Tour> tours = new ArrayList<Tour>();

        List<TourDaoModel> tourDaoModels = tourRepository.findAll();

         for (TourDaoModel tourDaoModel : tourDaoModels) {
            tours.add(TourConverter.toTour(tourDaoModel));
         }

         return tours;
    }


}
