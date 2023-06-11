package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourConverter;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourLogConverter;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.model.enums.ChildFriendliness;
import at.technikum.tolanzeilinger.tourplanner.model.enums.Popularity;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourLogRepository;
import at.technikum.tolanzeilinger.tourplanner.mapquest.models.TourDtoModel;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.mapquest.interfaces.MapquestService;
import at.technikum.tolanzeilinger.tourplanner.mapquest.interfaces.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.ImageStorageService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.helpers.StringUtilities;
import javafx.scene.image.Image;



import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// just for talking to the Repository
public class TourServiceImpl implements TourService {
    private final Logger logger;
    private final EventAggregator eventAggregator;

    // Repositories
    private final TourRepository tourRepository;

    private final TourLogRepository tourLogRepository;

    // Services
    private final MapquestUrlBuilderService mapquestUrlBuilderService;
    private final MapquestService mapquestService;

    private final ImageStorageService imageStorageService;

    // Miscellaneous
    private long activeTourIndex = -1;

    private Tour activeTour;

    private Image activeTourImage;

    private String fullTextSearchString = "";
    public TourServiceImpl(Logger logger,
                           EventAggregator eventAggregator,
                           TourRepository tourRepository,
                           MapquestService mapquestService,
                           MapquestUrlBuilderService mapquestUrlBuilderService,
                           ImageStorageService imageStorageService,
                           TourLogRepository tourLogRepository) {
        this.logger = logger;
        this.eventAggregator = eventAggregator;

        // Repositories
        this.tourRepository = tourRepository;
        this.tourLogRepository = tourLogRepository;

        // Services
        this.mapquestService = mapquestService;
        this.mapquestUrlBuilderService = mapquestUrlBuilderService;
        this.imageStorageService = imageStorageService;
    }
    @Override
    public void setFullTextSearchString(String fullTextSearchString) {
        this.fullTextSearchString = fullTextSearchString;
    }
    @Override
    public void addTour(Tour tour) {
        TourDtoModel routeFromUrl = fetchFromApi(tour);

        long id;

        if (routeFromUrl != null) {
            tour.setDistance(routeFromUrl.getDistance());
            tour.setEstimatedTime(routeFromUrl.getTime());
        } else {
            logger.warn("Could not fetch route from MapQuest");
            tour.setDistance(0);
            tour.setEstimatedTime(0);
            tour.setChildFriendliness(ChildFriendliness.NOT_FOR_CHILDREN);
        }

        tour.setPopularity(Popularity.NEVER_DONE);
        tour.setChildFriendliness(ChildFriendliness.getStatus(tour, new ArrayList<>()));

        id = tourRepository.create(TourConverter.toTourDaoModel(tour));

        if (id > 0 && routeFromUrl != null) {
            fetchImageForRouteAndSave(id, routeFromUrl.getSessionId());
            logger.info("NEW TOUR ID: " + id);
        }

        setActiveTourIndex(id);
    }
    private TourDtoModel fetchFromApi(Tour tour) {
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
        return routeFromUrl;
    }
    @Override
    public void setActiveTourIndex(long index) {
        this.activeTourIndex = index;

        setActiveTour();
        setActiveTourImage();

        eventAggregator.publish(Event.TOUR_CHANGED);
    }
    @Override
    public long getActiveTourIndex() {
        return activeTourIndex;
    }
    @Override
    public Tour getActiveTour() {
        return activeTour;
    }
    @Override
    public Image getActiveImage() {
        return activeTourImage;
    }
    @Override
    public void setActiveTourImage() {
        try {
            if(activeTourIndex >= 0) {
                activeTourImage = imageStorageService.loadImage(activeTour.getId());
            }
        } catch (NullPointerException e) {
            logger.error(e.getMessage(), e);

            activeTourImage = null;
        }
    }
    @Override
    public void setActiveTour() {
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
        var tour = tourRepository.find(id);
        tourRepository.delete(tour);

        setActiveTourIndex(-1);
    }

    @Override
    public void updateTourByIndex(long id, Tour newTour) {
        Tour oldTour = TourConverter.toTour(tourRepository.find(id));

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

        oldTour.setChildFriendliness(
                ChildFriendliness.getStatus(oldTour,
                        tourLogRepository
                                .findAllOfTour(TourConverter.toTourDaoModel(oldTour))
                                .stream()
                                .map(it -> TourLogConverter.toTourLog(it))
                                .toList()));

        TourDtoModel routeFromUrl = fetchFromApi(oldTour);

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
        //TODO: Replace this with a query that incorperates fulltext search
        //TODO: List<TourDaoModel> tourDaoModels = tourRepository.findAllThatMatch( this.fullTextSearchString  );

        for (TourDaoModel tourDaoModel : tourDaoModels) {
            // jerald winkler fuzzy search.

            if(!StringUtilities.isNullOrWhitespace(fullTextSearchString)){
                logger.info("Searching for: " + fullTextSearchString + " in: " + tourDaoModel.toString());
                Tour tour = TourConverter.toTour(tourDaoModel);
                StringBuilder tourString = new StringBuilder(tour.toSearchableString());

                List<TourLogDaoModel> tourLogs = tourLogRepository.findAllOfTour(tourDaoModel);

                for (TourLogDaoModel tourLogDaoModel : tourLogs) {
                    TourLog tourLog = TourLogConverter.toTourLog(tourLogDaoModel);

                    tourString.append(tourLog.toSearchableString());
                }

                // Compile the search query as a regex pattern
                Pattern pattern = Pattern.compile(fullTextSearchString, Pattern.CASE_INSENSITIVE);

                // Create a matcher for the object string
                Matcher matcher = pattern.matcher(tourString);

                // If at least one regex match is found, consider it a match
                if (matcher.find()) {
                    tours.add(tour);
                }

            } else {
                tours.add(TourConverter.toTour(tourDaoModel));
            }

        }

        return tours;
    }

    @Override
    public String getFullTextSearchString() {
        return fullTextSearchString;
    }

    @Override
    public Image getActiveTourImage() {
        return activeTourImage;
    }
    @Override
    public void setActiveTourImage(Image activeTourImage) {
        this.activeTourImage = activeTourImage;
    }

}
