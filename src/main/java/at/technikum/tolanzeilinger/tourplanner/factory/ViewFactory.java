package at.technikum.tolanzeilinger.tourplanner.factory;

import at.technikum.tolanzeilinger.tourplanner.constants.DefaultConstants;
import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogService;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourConverter;
import at.technikum.tolanzeilinger.tourplanner.log.Log4jLogger;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.persistence.HibernateSessionFactory;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.WordRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.implementation.TourLogRepositoryImpl;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.implementation.TourRepositoryImpl;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourLogRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.service.api.implementations.MapquestServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.service.api.implementations.MapquestUrlBuilderServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.service.api.interfaces.MapquestService;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.*;
import at.technikum.tolanzeilinger.tourplanner.service.api.interfaces.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.PropertyLoaderServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.*;
import at.technikum.tolanzeilinger.tourplanner.view.MainController;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.MainTabPaneSwitcherView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents.TourDataCreateView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents.TourDataDisplayView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents.TourDataEditView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents.TourDataPaneSwitcherView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourMapView;
import at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourMiscView;
import at.technikum.tolanzeilinger.tourplanner.view.MiscComponents.PDFcView;
import at.technikum.tolanzeilinger.tourplanner.view.MiscComponents.TopButtonsView;
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
import at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents.TopButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourListActionButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourListViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourLogsActionButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.view.TourLogComponents.TourLogListView;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourLogComponents.TourLogListViewModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.scene.image.Image;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
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
    private final PropertyLoaderService propertyLoaderService;
    private final MapquestUrlBuilderService mapquestUrlBuilderService;
    private final TourService tourService;
    private final TourLogService tourLogService;
    private final MapquestService mapquestService;
    private final ImageStorageService imageStorageService;
    private final DialogService dialogService;
    private final PdfService pdfService;

    // View Models
    private MainViewModel mainViewModel;
    private PDFcViewModel pdFcViewModel;
    private TopButtonsViewModel topButtonsViewModel;

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

    private final TourLogListViewModel tourLogListViewModel;

    // Logger
    private  Logger logger;

    private ViewFactory() {

        this.logger = Log4jLogger.getInstance();
        this.eventAggregator = new EventAggregator();
        this.hibernateSessionFactory = new HibernateSessionFactory();

        // TODO - COMMENT IF NOT NEEDED
        // clearDatabase(hibernateSessionFactory);

        this.viewMap = new HashMap<>();
        initializeViewMap();

        // initialize Repositories
        this.wordRepository = new WordRepository(eventAggregator);
        this.tourRepository = new TourRepositoryImpl(hibernateSessionFactory, eventAggregator, logger);
        this.tourLogRepository = new TourLogRepositoryImpl(hibernateSessionFactory, eventAggregator, logger);

        // initialize Services
        this.propertyLoaderService = new PropertyLoaderServiceImpl(DefaultConstants.CONFIG_PATH, logger, eventAggregator);
        this.imageStorageService = new ImageStorageStorageServiceImpl(propertyLoaderService, eventAggregator, logger);
        this.mapquestUrlBuilderService = new MapquestUrlBuilderServiceImpl(propertyLoaderService);
        this.mapquestService = new MapquestServiceImpl(mapquestUrlBuilderService);
        this.tourService = new TourServiceImpl(logger, eventAggregator, tourRepository, mapquestService, mapquestUrlBuilderService, imageStorageService);
        this.tourLogService = new TourLogServiceImpl(tourLogRepository, tourService, logger, eventAggregator);
        this.pdfService = new PdfServiceImpl(logger, eventAggregator);

        this.dialogService = new DialogService(logger, eventAggregator, tourService, tourLogService);

        // initialize ViewModels
        this.mainViewModel = new MainViewModel(eventAggregator, wordRepository, logger, mapquestService, imageStorageService);

        this.pdFcViewModel = new PDFcViewModel();
        this.topButtonsViewModel = new TopButtonsViewModel(eventAggregator, logger);

        this.tourListActionButtonsViewModel = new TourListActionButtonsViewModel(eventAggregator, logger);
        this.tourListViewModel = new TourListViewModel(eventAggregator, logger, tourService);

        this.tourLogsActionButtonsViewModel = new TourLogsActionButtonsViewModel(eventAggregator, logger);
        this.tourLogListViewModel = new TourLogListViewModel(eventAggregator, logger, tourLogService);

        this.mainTabPaneSwitcherViewModel = new MainTabPaneSwitcherViewModel(eventAggregator, logger);

        this.tourDataPaneSwitcherViewModel = new TourDataPaneSwitcherViewModel(eventAggregator, logger);
        this.tourDataDisplayViewModel = new TourDataDisplayViewModel(eventAggregator, logger, tourService);
        this.tourDataEditViewModel = new TourDataEditViewModel(eventAggregator, logger, tourService);
        this.tourDataCreateViewModel = new TourDataCreateViewModel(eventAggregator, logger, tourService);


        this.tourMapViewModel = new TourMapViewModel(eventAggregator, tourService, logger);
        this.tourMiscViewModel = new TourMiscViewModel(eventAggregator, tourService, logger);

        // checkDatabase(hibernateSessionFactory);
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
        viewMap.put(TourLogListView.class, () -> new TourLogListView(tourLogListViewModel));

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
        viewMap.put(TopButtonsView.class, () -> new TopButtonsView(topButtonsViewModel));
    
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

    private void clearDatabase(HibernateSessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            Query query = session.createNativeQuery("TRUNCATE TABLE tp_tour, tp_tour_log");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
