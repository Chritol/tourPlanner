package at.technikum.tolanzeilinger.tourplanner.factory;

import at.technikum.tolanzeilinger.tourplanner.constants.DefaultConstants;
import at.technikum.tolanzeilinger.tourplanner.presentation.dialogs.DialogService;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Log4jLogger;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.persistence.HibernateSessionFactory;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.implementation.TourLogRepositoryImpl;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.implementation.TourRepositoryImpl;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourLogRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.mapquest.implementations.MapquestServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.mapquest.implementations.MapquestUrlBuilderServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.mapquest.interfaces.MapquestService;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.*;
import at.technikum.tolanzeilinger.tourplanner.mapquest.interfaces.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.PropertyLoaderServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.*;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.MainTabPaneSwitcherView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.TourDataComponents.TourDataCreateView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.TourDataComponents.TourDataDisplayView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.TourDataComponents.TourDataEditView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.TourDataComponents.TourDataPaneSwitcherView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.TourMapView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.TourMiscView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MiscComponents.LogLineChartView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MiscComponents.PDFcView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MiscComponents.SearchView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MiscComponents.TopButtonsView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.TourListComponents.TourListActionButtonsView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.TourListComponents.TourListView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.TourLogComponents.TourLogsActionButtonsView;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.MainTabPaneSwitcherViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourDataComponents.TourDataCreateViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourDataComponents.TourDataDisplayViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourDataComponents.TourDataEditViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourDataComponents.TourDataPaneSwitcherViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourMapViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourMiscViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents.LogLineChartViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents.PDFcViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents.SearchViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents.TopButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.TourListComponents.TourListActionButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.TourListComponents.TourListViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.TourLogComponents.TourLogsActionButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.TourLogComponents.TourLogListView;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.TourLogComponents.TourLogListViewModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ViewFactory {
    private static ViewFactory instance;
    private final HibernateSessionFactory hibernateSessionFactory;
    private final Map<Class<?>, Supplier<Object>> viewMap;
    private final EventAggregator eventAggregator;

    // Repositories
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
    private final FolderOpenerService folderOpenerService;
    private final ExportDataService exportDataService;
    private final ImportDataService importDataService;
    private final FilePickerService filePickerService;

    // View Models
    private final PDFcViewModel pdFcViewModel;
    private final TopButtonsViewModel topButtonsViewModel;
    private final SearchViewModel searchViewModel;

    private final MainTabPaneSwitcherViewModel mainTabPaneSwitcherViewModel;

    private final TourDataPaneSwitcherViewModel tourDataPaneSwitcherViewModel;
    private final TourDataDisplayViewModel tourDataDisplayViewModel;
    private final TourDataEditViewModel tourDataEditViewModel;
    private final TourDataCreateViewModel tourDataCreateViewModel;

    private final TourMapViewModel tourMapViewModel;
    private final TourMiscViewModel tourMiscViewModel;
    private final LogLineChartViewModel logLineChartViewModel;

    private final TourListActionButtonsViewModel tourListActionButtonsViewModel;
    private final TourListViewModel tourListViewModel;
    private final TourLogsActionButtonsViewModel tourLogsActionButtonsViewModel;

    private final TourLogListViewModel tourLogListViewModel;

    // Logger
    private final Logger logger;

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

    private ViewFactory() {
        this.logger = Log4jLogger.getInstance();
        this.eventAggregator = new EventAggregator();
        this.hibernateSessionFactory = new HibernateSessionFactory();

        // TODO - COMMENT IF NOT NEEDED
        // clearDatabase(hibernateSessionFactory);

        this.viewMap = new HashMap<>();
        initializeViewMap();

        // initialize Repositories
        this.tourRepository = new TourRepositoryImpl(hibernateSessionFactory, eventAggregator, logger);
        this.tourLogRepository = new TourLogRepositoryImpl(hibernateSessionFactory, eventAggregator, logger);

        // initialize Services
        this.propertyLoaderService = new PropertyLoaderServiceImpl(DefaultConstants.CONFIG_PATH, logger, eventAggregator);
        this.mapquestUrlBuilderService = new MapquestUrlBuilderServiceImpl(propertyLoaderService);
        this.mapquestService = new MapquestServiceImpl(mapquestUrlBuilderService);

        this.filePickerService = new FilePickerServiceImpl();
        this.folderOpenerService = new FolderOpenerServiceImpl(propertyLoaderService, logger, eventAggregator);
        this.imageStorageService = new ImageStorageStorageServiceImpl(propertyLoaderService, eventAggregator, logger, folderOpenerService);

        this.tourService = new TourServiceImpl(logger, eventAggregator, tourRepository, mapquestService, mapquestUrlBuilderService, imageStorageService, tourLogRepository);
        this.tourLogService = new TourLogServiceImpl(tourLogRepository, tourService, logger, eventAggregator, tourRepository);

        this.pdfService = new PdfServiceImpl(propertyLoaderService, logger, eventAggregator, tourService, tourLogService, folderOpenerService);
        this.importDataService = new ImportDataServiceImpl(logger, tourService, tourLogService);
        this.exportDataService = new ExportDataServiceImpl(logger, tourRepository, tourLogRepository, propertyLoaderService, folderOpenerService, eventAggregator);

        this.dialogService = new DialogService(logger, eventAggregator, tourService, tourLogService);

        // initialize ViewModels
        this.pdFcViewModel = new PDFcViewModel(eventAggregator, logger, tourService, exportDataService, pdfService, importDataService, filePickerService);
        this.topButtonsViewModel = new TopButtonsViewModel(eventAggregator, logger);
        this.searchViewModel = new SearchViewModel(eventAggregator, logger, tourService);

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
        this.logLineChartViewModel = new LogLineChartViewModel(tourLogService, eventAggregator, tourService);
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
        viewMap.put(LogLineChartView.class, () -> new LogLineChartView(logLineChartViewModel));
    
        viewMap.put(PDFcView.class, () -> new PDFcView(pdFcViewModel));
        viewMap.put(TopButtonsView.class, () -> new TopButtonsView(topButtonsViewModel));
        viewMap.put(SearchView.class, () -> new SearchView(searchViewModel));
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
