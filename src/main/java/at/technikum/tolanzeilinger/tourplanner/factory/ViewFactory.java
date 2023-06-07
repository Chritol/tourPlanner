package at.technikum.tolanzeilinger.tourplanner.factory;

import at.technikum.tolanzeilinger.tourplanner.constants.DefaultConstants;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Log4jLogger;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.HibernateSessionFactory;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.TourDao;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.TourLogDao;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.Difficulty;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationType;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.WordRepository;
import at.technikum.tolanzeilinger.tourplanner.service.helperServices.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.properties.PropertyLoader;
import at.technikum.tolanzeilinger.tourplanner.service.TourService;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.RouteService;
import at.technikum.tolanzeilinger.tourplanner.view.MainController;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourMapView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourMiscView;
import at.technikum.tolanzeilinger.tourplanner.view.MiscComponents.PDFcView;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourMapViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourMiscViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents.PDFcViewModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ViewFactory {
    private static ViewFactory instance;

    private final Map<Class<?>, Supplier<Object>> viewMap;

    private final EventAggregator eventAggregator;

    // Repositories
    private final WordRepository wordRepository;
    private final TourRepository tourRepository;

    // Services
    private final PropertyLoader propertyLoader;
    private final MapquestUrlBuilderService mapquestUrlBuilderService;
    private final TourService tourService;
    private final RouteService routeService;

    // View Models
    private final MainViewModel mainViewModel;
    private final PDFcViewModel pdFcViewModel;
    private final TourDataViewModel tourDataViewModel;
    private final TourMapViewModel tourMapViewModel;
    private final TourMiscViewModel tourMiscViewModel;

    // Logger
    private  Logger logger;


    private ViewFactory() {

        this.logger = Log4jLogger.getInstance();
        this.eventAggregator = new EventAggregator();

        this.viewMap = new HashMap<>();
        initializeViewMap();

        this.propertyLoader = new PropertyLoader(DefaultConstants.CONFIG_PATH, logger, eventAggregator);

        // initialize Repositories
        this.wordRepository = new WordRepository(eventAggregator);
        this.tourRepository = new TourRepository(eventAggregator);

        // initialize Services
        this.mapquestUrlBuilderService = new MapquestUrlBuilderService(propertyLoader);
        this.routeService = new RouteService();
        this.tourService = new TourService(logger, eventAggregator, tourRepository, routeService, mapquestUrlBuilderService);

        // initialize ViewModels
        this.mainViewModel = new MainViewModel(eventAggregator, wordRepository, logger);
        this.pdFcViewModel = new PDFcViewModel();

        this.tourDataViewModel = new TourDataViewModel(eventAggregator, tourService, logger);
        this.tourMapViewModel = new TourMapViewModel(eventAggregator, tourService, logger);
        this.tourMiscViewModel = new TourMiscViewModel(eventAggregator, tourService, logger);

        HibernateSessionFactory hibernateSessionFactory = new HibernateSessionFactory();

        // addTestDataToDb(hibernateSessionFactory);
        checkDatabase(hibernateSessionFactory);
    }

    public static ViewFactory getInstance() {
        if(instance == null)
            instance = new ViewFactory();
        return instance;
    }

    public Object createView(Class<?> controllerClass) {
        Supplier<Object> controllerSupplier = viewMap.get(controllerClass);
        if (controllerSupplier != null) {
            return controllerSupplier.get();
        }

        logger.fatal("No controller found for class: " + controllerClass.getName());

        throw new NullPointerException();
    }

    private void initializeViewMap() {
        viewMap.put(TourDataView.class, () -> new TourDataView(tourDataViewModel));
        viewMap.put(TourMapView.class, () -> new TourMapView(tourMapViewModel));
        viewMap.put(TourMiscView.class, () -> new TourMiscView(tourMiscViewModel));
        viewMap.put(PDFcView.class, () -> new PDFcView(pdFcViewModel));
        viewMap.put(MainController.class, () -> new MainController(mainViewModel));
    }

    private void checkDatabase(HibernateSessionFactory sessionFactory) {
        // TODO remove later - only for debugging

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TourLogDao> criteria = builder.createQuery(TourLogDao.class);
            criteria.from(TourLogDao.class);

            List<TourLogDao> tourLogs = session.createQuery(criteria).getResultList();
            logger.info("Total logs" + tourLogs.stream().count());
            for (TourLogDao tourLog : tourLogs) {
                logger.info(tourLog.toString());
            }
        }
    }

    private void addTestDataToDb(HibernateSessionFactory sessionFactory) {
        // TODO remove later - logic needed for repositories
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            TourDao tour1 = new TourDao("Tour 1", "Description 1", "From 1", "To 1",
                    TransportationType.CAR, 100, 120, HillType.AVOID_ALL_HILLS);
            TourDao tour2 = new TourDao("Tour 2", "Description 2", "From 2", "To 2",
                    TransportationType.BIKE, 150, 180, HillType.FAVOR_UP_HILL);
            session.persist(tour1);
            session.persist(tour2);

            TourLogDao log1 = new TourLogDao(tour1, LocalDateTime.now(), "Comment 1",
                    Difficulty.EASY, 5, 4);
            TourLogDao log2 = new TourLogDao(tour1, LocalDateTime.now(), "Comment 2",
                    Difficulty.INTERMEDIATE, 6, 3);
            TourLogDao log3 = new TourLogDao(tour2, LocalDateTime.now(), "Comment 3",
                    Difficulty.HARD, 6, 5);
            session.persist(log1);
            session.persist(log2);
            session.persist(log3);

            tx.commit();
        }
    }
}
