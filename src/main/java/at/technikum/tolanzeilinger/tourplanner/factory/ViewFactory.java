package at.technikum.tolanzeilinger.tourplanner.factory;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Log4jLogger;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.WordRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.HibernateSessionFactory;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.TourDao;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.TourLogDao;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.Difficulty;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationType;
import at.technikum.tolanzeilinger.tourplanner.view.MainController;
import at.technikum.tolanzeilinger.tourplanner.view.PDFcView;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.PDFcViewModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public class ViewFactory {
    private static ViewFactory instance;

    private final EventAggregator eventAggregator;

    // Repositories
    private final WordRepository wordRepository;

    // View Models
    private final MainViewModel mainViewModel;
    private final PDFcViewModel pdFcViewModel;

    // Logger
    private final Logger logger;

    private ViewFactory() {

        this.logger = new Log4jLogger();
        this.eventAggregator = new EventAggregator();

        // initialize Repositories
        this.wordRepository = new WordRepository(eventAggregator);

        // initialize ViewModels
        this.mainViewModel = new MainViewModel(eventAggregator, wordRepository, logger);

        this.pdFcViewModel = new PDFcViewModel();

        HibernateSessionFactory hibernateSessionFactory = new HibernateSessionFactory();

        addTestDataToDb(hibernateSessionFactory);
        checkDatabase(hibernateSessionFactory);
    }

    public static ViewFactory getInstance() {
        if(instance == null)
            instance = new ViewFactory();
        return instance;
    }

    public Object create(Class<?> controllerClass) {
        if(controllerClass == PDFcView.class)
            return new PDFcView(pdFcViewModel);
        if(controllerClass == MainController.class)
            return new MainController(mainViewModel);

        throw new NullPointerException();
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
