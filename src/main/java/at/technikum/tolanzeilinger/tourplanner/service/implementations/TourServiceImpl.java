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
        var allTours = this.tourRepository.findAll();
        if (allTours.size() > 0) {
            setActiveTourIndex(allTours.get(0).getId());
        }

        eventAggregator.publish(Event.TOUR_LOADED);
    }

    public void addTour(Tour tour) {
        TourDtoModel routeFromUrl = null;
        try {
            routeFromUrl = mapquestService.fetchMapquestRoute(mapquestUrlBuilderService.buildDirectionsUrl(tour.getFrom(), tour.getTo()));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        long id = activeTourIndex;

        if(routeFromUrl != null) {
            tour.setDistance(routeFromUrl.getDistance());
            tour.setEstimatedTime(routeFromUrl.getTime());

            id = tourRepository.create(TourConverter.toTourDaoModel(tour));

            if (id > 0) {
                fetchImageForRouteAndSave(id, routeFromUrl.getSessionId());
                logger.info("NEW TOUR ID:" + id);
            }
        } else {
            logger.warn("Could not fetch route from MapQuest");

            tour.setDistance(0);
            tour.setEstimatedTime(0);

            id = tourRepository.create(TourConverter.toTourDaoModel(tour));
        }

        setActiveTourIndex(id);
    }

    public void setActiveTourIndex(long index) {
        this.activeTourIndex = index;

        setActiveTour();
        setActiveTourImage();

        eventAggregator.publish(Event.TOUR_CHANGED);
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
                    this.tourRepository.find(this.activeTourIndex)
            );
        }
    }

    private void fetchImageForRouteAndSave(long id, String sessionId) {
        try {
            InputStream imageInputStream = mapquestService.fetchMapquestImage(mapquestUrlBuilderService.buildMapImageUrl(sessionId));

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
    public void deleteTourByIndex(long id) {
        var tour = tourRepository.read(id);
        tourRepository.delete(tour);

        setActiveTourIndex(-1);
    }

    @Override
    public void updateTourByIndex(long id, Tour newTour) {
        Tour oldTour = TourConverter.toTour(tourRepository.read(id));

        if (newTour.getName() != null && !newTour.getName().isEmpty() && newTour.getName().length() <= 50) {
            oldTour.setName(newTour.getName());
        }

        if (newTour.getDescription() != null && !newTour.getDescription().isEmpty() && newTour.getDescription().length() <= 100) {
            oldTour.setDescription(newTour.getDescription());
        }

        if (newTour.getFrom() != null && !newTour.getFrom().isEmpty() && newTour.getFrom().length() <= 100) {
            oldTour.setFrom(newTour.getFrom());
        }

        if (newTour.getTo() != null && !newTour.getTo().isEmpty() && newTour.getTo().length() <= 100) {
            oldTour.setTo(newTour.getTo());
        }

        if (newTour.getTransportation() != null) {
            oldTour.setTransportation(newTour.getTransportation());
        }

        if (newTour.getHilliness() != null) {
            oldTour.setHilliness(newTour.getHilliness());
        }

        TourDtoModel routeFromUrl = null;
        try {
            routeFromUrl = mapquestService.fetchMapquestRoute(mapquestUrlBuilderService.buildDirectionsUrl(oldTour.getFrom(), oldTour.getTo()));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }


        if(routeFromUrl != null) {
            oldTour.setDistance(routeFromUrl.getDistance());
            oldTour.setEstimatedTime(routeFromUrl.getTime());

            if (id > 0) {
                fetchImageForRouteAndSave(id, routeFromUrl.getSessionId());
                logger.info("NEW TOUR ID:" + id);
            }
        } else {
            logger.warn("Could not fetch route from MapQuest");
        }

        TourDaoModel resultTour = TourConverter.toTourDaoModel(oldTour);
        resultTour.setId(id);

        tourRepository.update(resultTour);

        setActiveTourIndex(id);
    }

    @Override
    public List<Tour> getTours() {
        // Get all tours
        List<Tour> tours = new ArrayList<>();

        List<TourDaoModel> tourDaoModels = tourRepository.findAll();

         for (TourDaoModel tourDaoModel : tourDaoModels) {
            tours.add(TourConverter.toTour(tourDaoModel));
         }

         return tours;
    }


}
