package at.technikum.tolanzeilinger.tourplanner.persistence.repositories.implementation;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.persistence.HibernateSessionFactory;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.helpers.TourDaoModelValidator;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.interfaces.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class TourRepositoryImpl implements TourRepository {
    private final HibernateSessionFactory sessionFactory;
    private final EventAggregator eventAggregator;

    private final Logger logger;

    public TourRepositoryImpl(HibernateSessionFactory sessionFactory, EventAggregator eventAggregator, Logger logger) {
        this.sessionFactory = sessionFactory;
        this.eventAggregator = eventAggregator;
        this.logger = logger;
    }

    @Override
    public Long create(TourDaoModel tour) {
        Long tourId = -1L;

        if(!TourDaoModelValidator.isValid(tour)) {
            logger.warn("Invalid tour:" + (tour != null ? tour.toString() : "tour is null"));
        } else {
            try (Session session = sessionFactory.openSession()) {
                session.getTransaction().begin();
                session.persist(tour);
                session.getTransaction().commit();

                tourId = tour.getId();

                eventAggregator.publish(Event.TOUR_CREATED);
            } catch (Exception e) {
                logger.error("Failed to create tour: " + e.getMessage(), e);
            }
        }

        return tourId;
    }

    @Override
    public TourDaoModel read(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(TourDaoModel.class, id);
        } catch (NoResultException e) {
            logger.warn("No tours available in the database with id: " + id);
            return null;
        } catch (Exception e) {
            logger.error("Failed to read tour with id: " + id + ". Error: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean update(TourDaoModel tour) {
        if (!TourDaoModelValidator.isValid(tour)) {
            logger.warn("Invalid tour: " + (tour != null ? tour.toString() : "tour is null"));
            return false;
        } else {
            try (Session session = sessionFactory.openSession()) {
                session.getTransaction().begin();
                session.merge(tour);
                session.getTransaction().commit();

                eventAggregator.publish(Event.TOUR_UPDATED);
                return true;
            } catch (Exception e) {
                logger.error("Failed to update tour: " + e.getMessage(), e);
                return false;
            }
        }
    }

    @Override
    public boolean delete(TourDaoModel tour) {
        if (tour == null) {
            logger.warn("Cannot delete null tour");
            return false;
        } else {
            try (Session session = sessionFactory.openSession()) {
                session.getTransaction().begin();
                session.delete(tour);
                session.getTransaction().commit();

                eventAggregator.publish(Event.TOUR_DELETED);
                return true;
            } catch (Exception e) {
                logger.error("Failed to delete tour: " + e.getMessage(), e);
                return false;
            }
        }
    }

    @Override
    public List<TourDaoModel> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM TourDaoModel", TourDaoModel.class);
            return query.getResultList();
        } catch (NoResultException e) {
            logger.warn("No tours available in the database");
            return null;
        } catch (Exception e) {
            logger.error("Failed to retrieve all tours: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public TourDaoModel findFirst() {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM TourDaoModel", TourDaoModel.class);
            query.setMaxResults(1);
            return (TourDaoModel) query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("No tours available in the database");
            return null;
        } catch (Exception e) {
            logger.error("Failed to retrieve first tour: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public TourDaoModel findFirst(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM TourDaoModel WHERE name = :name", TourDaoModel.class);
            query.setParameter("name", name);
            query.setMaxResults(1);
            return (TourDaoModel) query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("No tours available in the database with name: " + name);
            return null;
        } catch (Exception e) {
            logger.error("Failed to retrieve first tour with name: " + name + ". Error: " + e.getMessage(), e);
            return null;
        }
    }
}
