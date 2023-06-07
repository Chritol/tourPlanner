package at.technikum.tolanzeilinger.tourplanner.factory;

import at.technikum.tolanzeilinger.tourplanner.constants.DefaultConstants;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Log4jLogger;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.persistence.HibernateSessionFactory;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.Difficulty;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationType;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.WordRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.implementation.TourLogRepositoryImpl;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.implementation.TourRepositoryImpl;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourLogRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.MapquestUrlBuilderServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.properties.PropertyLoader;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.TourServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.MapquestServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.MapquestService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.view.MainController;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.MainTabPaneSwitcherView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents.TourDataCreateView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents.TourDataDisplayView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents.TourDataEditView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents.TourDataPaneSwitcherView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourMapView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourMiscView;
import at.technikum.tolanzeilinger.tourplanner.view.MiscComponents.PDFcView;
import at.technikum.tolanzeilinger.tourplanner.view.TourListComponents.TourListActionButtonsView;
import at.technikum.tolanzeilinger.tourplanner.view.TourListComponents.TourListView;
import at.technikum.tolanzeilinger.tourplanner.view.TourLogComponents.TourLogsActionButtonsView;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.MainTabPaneSwitcherViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataCreateViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataDisplayViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataEditViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataPaneSwitcherViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourMapViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourMiscViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents.PDFcViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourListActionButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourListViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourLogsActionButtonsViewModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ViewFactory {
    private static ViewFactory instance;
    private final HibernateSessionFactory hibernateSessionFactory;

    private final Map<Class<?>, Supplier<Object>> viewMap;

    private final EventAggregator eventAggregator;

    // Repositories
    private final WordRepository wordRepository;
    private final TourRepository tourRepository;

    private final TourLogRepository tourLogRepository;

    // Services
    private final PropertyLoader propertyLoader;
    private final MapquestUrlBuilderService mapquestUrlBuilderService;
    private final TourService tourService;
    private final MapquestService mapquestService;

    // View Models
    private MainViewModel mainViewModel;
    private  PDFcViewModel pdFcViewModel;

    private MainTabPaneSwitcherViewModel mainTabPaneSwitcherViewModel;

    private TourDataPaneSwitcherViewModel tourDataPaneSwitcherViewModel;
    private TourDataDisplayViewModel tourDataDisplayViewModel;
    private TourDataEditViewModel tourDataEditViewModel;
    private TourDataCreateViewModel tourDataCreateViewModel;



    private  TourMapViewModel tourMapViewModel;
    private  TourMiscViewModel tourMiscViewModel;

    private final TourListActionButtonsViewModel tourListActionButtonsViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourLogsActionButtonsViewModel tourLogsActionButtonsViewModel;



    // Logger
    private  Logger logger;


    private ViewFactory() {

        this.logger = Log4jLogger.getInstance();
        this.eventAggregator = new EventAggregator();
        this.hibernateSessionFactory = new HibernateSessionFactory();

        // TODO - COMMENT IF NOT NEEDED
        //clearDatabase(hibernateSessionFactory);

        this.viewMap = new HashMap<>();
        initializeViewMap();

        this.propertyLoader = new PropertyLoader(DefaultConstants.CONFIG_PATH, logger, eventAggregator);

        // initialize Repositories
        this.wordRepository = new WordRepository(eventAggregator);
        this.tourRepository = new TourRepositoryImpl(hibernateSessionFactory, eventAggregator, logger);
        this.tourLogRepository = new TourLogRepositoryImpl(hibernateSessionFactory, eventAggregator, logger);

        // initialize Services
        this.mapquestUrlBuilderService = new MapquestUrlBuilderServiceImpl(propertyLoader);
        this.mapquestService = new MapquestServiceImpl();
        this.tourService = new TourServiceImpl(logger, eventAggregator, tourRepository, mapquestService, mapquestUrlBuilderService);

        // initialize ViewModels
        this.mainViewModel = new MainViewModel(eventAggregator, wordRepository, logger);

        this.pdFcViewModel = new PDFcViewModel();

        this.tourListActionButtonsViewModel = new TourListActionButtonsViewModel(eventAggregator, logger);
        this.tourListViewModel = new TourListViewModel(eventAggregator, logger, tourService);

        this.tourLogsActionButtonsViewModel = new TourLogsActionButtonsViewModel(eventAggregator, logger);

        this.mainTabPaneSwitcherViewModel = new MainTabPaneSwitcherViewModel(eventAggregator, logger);

        this.tourDataPaneSwitcherViewModel = new TourDataPaneSwitcherViewModel(eventAggregator, logger);
        this.tourDataDisplayViewModel = new TourDataDisplayViewModel(eventAggregator, logger, tourService);
        this.tourDataEditViewModel = new TourDataEditViewModel(eventAggregator, logger, tourService);
        this.tourDataCreateViewModel = new TourDataCreateViewModel(eventAggregator, logger, tourService);


        this.tourMapViewModel = new TourMapViewModel(eventAggregator, tourService, logger);
        this.tourMiscViewModel = new TourMiscViewModel(eventAggregator, tourService, logger);

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
        // List Pane
        viewMap.put(TourListActionButtonsView.class, () -> new TourListActionButtonsView(tourListActionButtonsViewModel));
        viewMap.put(TourListView.class, () -> new TourListView(tourListViewModel));


        viewMap.put(TourLogsActionButtonsView.class, () -> new TourLogsActionButtonsView(tourLogsActionButtonsViewModel));

        // Main Tab Pane
        viewMap.put(MainTabPaneSwitcherView.class, () -> new MainTabPaneSwitcherView(mainTabPaneSwitcherViewModel));
        viewMap.put(TourListActionButtonsView.class, () -> new TourListActionButtonsView(tourListActionButtonsViewModel));

        viewMap.put(TourLogsActionButtonsView.class, () -> new TourLogsActionButtonsView(tourLogsActionButtonsViewModel));

        // Main Tab Pane
        viewMap.put(MainTabPaneSwitcherView.class, () -> new MainTabPaneSwitcherView(mainTabPaneSwitcherViewModel));
    
        viewMap.put(TourDataPaneSwitcherView.class, () -> new TourDataPaneSwitcherView(tourDataPaneSwitcherViewModel));
        viewMap.put(TourDataCreateView.class, () -> new TourDataCreateView(tourDataCreateViewModel));
        viewMap.put(TourDataEditView.class, () -> new TourDataEditView(tourDataEditViewModel));
        viewMap.put(TourDataDisplayView.class, () -> new TourDataDisplayView(tourDataDisplayViewModel));
    
        viewMap.put(TourMapView.class, () -> new TourMapView(tourMapViewModel));
        viewMap.put(TourMiscView.class, () -> new TourMiscView(tourMiscViewModel));
    
        viewMap.put(PDFcView.class, () -> new PDFcView(pdFcViewModel));
    
        viewMap.put(MainController.class, () -> new MainController(mainViewModel));
    }

    private void checkDatabase(HibernateSessionFactory sessionFactory) {
        // TODO remove later - only for debugging

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TourLogDaoModel> criteria = builder.createQuery(TourLogDaoModel.class);
            criteria.from(TourLogDaoModel.class);

            List<TourLogDaoModel> tourLogs = session.createQuery(criteria).getResultList();
            logger.info("Total logs" + tourLogs.stream().count());
            for (TourLogDaoModel tourLog : tourLogs) {
                logger.info(tourLog.toString());
            }
        }
    }

    public void clearDatabase(HibernateSessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createNativeQuery("TRUNCATE TABLE tp_tour, tp_tour_log");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void addTestDataToDb(HibernateSessionFactory sessionFactory) {
        // TODO remove later - logic needed for repositories
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            TourDaoModel tour1 = new TourDaoModel("Tour 1", "Description 1", "From 1", "To 1",
                    TransportationType.CAR, 100, 120, HillType.AVOID_ALL_HILLS);
            TourDaoModel tour2 = new TourDaoModel("Tour 2", "Description 2", "From 2", "To 2",
                    TransportationType.BIKE, 150, 180, HillType.FAVOR_UP_HILL);
            session.persist(tour1);
            session.persist(tour2);

            TourLogDaoModel log1 = new TourLogDaoModel(tour1, LocalDateTime.now(), "Comment 1",
                    Difficulty.EASY, 5, 4);
            TourLogDaoModel log2 = new TourLogDaoModel(tour1, LocalDateTime.now(), "Comment 2",
                    Difficulty.INTERMEDIATE, 6, 3);
            TourLogDaoModel log3 = new TourLogDaoModel(tour2, LocalDateTime.now(), "Comment 3",
                    Difficulty.HARD, 6, 5);
            session.persist(log1);
            session.persist(log2);
            session.persist(log3);

            tx.commit();
        }
    }
}
