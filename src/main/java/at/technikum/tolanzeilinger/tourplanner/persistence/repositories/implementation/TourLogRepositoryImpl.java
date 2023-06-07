package at.technikum.tolanzeilinger.tourplanner.persistence.repositories.implementation;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.persistence.HibernateSessionFactory;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.helpers.TourLogDaoModelValidator;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourLogDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourLogRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;

public class TourLogRepositoryImpl implements TourLogRepository {
    private final HibernateSessionFactory sessionFactory;
    private final EventAggregator eventAggregator;
    private final Logger logger;

    public TourLogRepositoryImpl(HibernateSessionFactory sessionFactory, EventAggregator eventAggregator, Logger logger) {
        this.sessionFactory = sessionFactory;
        this.eventAggregator = eventAggregator;
        this.logger = logger;
    }

    @Override
    public Long create(TourLogDaoModel tourLog) {
        Long tourLogId = -1L;

        if (!TourLogDaoModelValidator.isValid(tourLog)) {
            logger.warn("Invalid tour log: " + (tourLog != null ? tourLog.toString() : "tour log is null"));
        } else {
            try (Session session = sessionFactory.openSession()) {
                session.getTransaction().begin();
                session.persist(tourLog);
                session.getTransaction().commit();

                tourLogId = tourLog.getId();
                eventAggregator.publish(Event.LOG_CREATED);
            } catch (Exception e) {
                logger.error("Failed to create tour log: " + e.getMessage(), e);
            }
        }

        return tourLogId;
    }

    @Override
    public TourLogDaoModel read(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(TourLogDaoModel.class, id);
        } catch (NoResultException e) {
            logger.warn("No tours log available in the database with id: " + id);
            return null;
        } catch (Exception e) {
            logger.error("Failed to read tour log with id: "+ id + ". Error: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean update(TourLogDaoModel tourLog) {
        if (!TourLogDaoModelValidator.isValid(tourLog)) {
            logger.warn("Invalid tour log: " + (tourLog != null ? tourLog.toString() : "tour log is null"));
            return false;
        } else {
            try (Session session = sessionFactory.openSession()) {
                session.getTransaction().begin();
                session.merge(tourLog);
                session.getTransaction().commit();

                eventAggregator.publish(Event.LOG_UPDATED);
                return true;
            } catch (Exception e) {
                logger.error("Failed to update tour log: " + e.getMessage(), e);
                return false;
            }
        }
    }

    @Override
    public boolean delete(TourLogDaoModel tourLog) {
        if (tourLog == null) {
            logger.warn("Cannot delete null tour log");
            return false;
        } else {
            try (Session session = sessionFactory.openSession()) {
                session.getTransaction().begin();
                session.delete(tourLog);
                session.getTransaction().commit();

                eventAggregator.publish(Event.LOG_DELETED);
                return true;
            } catch (Exception e) {
                logger.error("Failed to delete tour log: " + e.getMessage(), e);
                return false;
            }
        }
    }

    @Override
    public List<TourLogDaoModel> findAllLogs(TourDaoModel tour) {
        if (tour == null) {
            logger.warn("Cannot delete null tour");
            return null;
        } else {
            try (Session session = sessionFactory.openSession()) {
                TypedQuery query = session.createQuery("SELECT l FROM TourLogDaoModel l WHERE l.tour = :tour", TourLogDaoModel.class);
                query.setParameter("tour", tour);
                return query.getResultList();
            } catch (NoResultException e) {
                logger.warn("No tours logs available in the database for model with id: " + tour.getId());
                return null;
            } catch (Exception e) {
                logger.error("Failed to retrieve logs for tour with ID: " + tour.getId() + ". Error: " + e.getMessage(), e);
                return null;
            }
        }
    }
}
