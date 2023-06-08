package repositories;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.persistence.HibernateSessionFactory;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationType;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.implementation.TourRepositoryImpl;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TourRepositoryTest {
    private TourRepositoryImpl tourRepository;
    private HibernateSessionFactory sessionFactory;
    private EventAggregator eventAggregator;
    private Logger logger;

    @BeforeEach
    public void setUp() {
        sessionFactory = Mockito.mock(HibernateSessionFactory.class);
        eventAggregator = Mockito.mock(EventAggregator.class);
        logger = Mockito.mock(Logger.class);
        tourRepository = new TourRepositoryImpl(sessionFactory, eventAggregator, logger);
    }

    @Test
    public void testCreate_ValidTour_ReturnsTourId() {
        // Arrange
        TourDaoModel tour = mock(TourDaoModel.class, withSettings().useConstructor(
                "Test",
                "Test",
                "Wien",
                "Linz",
                TransportationType.BIKE,
                100,
                100,
                HillType.AVOID_UP_HILL
        ));

        Session sessionMock = mock(Session.class);
        Transaction transactionMock = mock(Transaction.class);
        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.getTransaction()).thenReturn(transactionMock);

        when(tour.getName()).thenCallRealMethod();
        when(tour.getDescription()).thenCallRealMethod();
        when(tour.getDestinationFrom()).thenCallRealMethod();
        when(tour.getDestinationTo()).thenCallRealMethod();
        when(tour.getTransportationType()).thenCallRealMethod();
        when(tour.getDistance()).thenCallRealMethod();
        when(tour.getEstimatedTime()).thenCallRealMethod();
        when(tour.getHillType()).thenCallRealMethod();
        when(tour.getId()).thenReturn(1L);

        String test = tour.getName();
        // Act
        Long tourId = tourRepository.create(tour);

        // Assert
        assertNotNull(tourId);
        assertNotEquals(-1L, tourId);
        verify(eventAggregator).publish(Event.TOUR_CREATED);
    }

    @Test
    public void testCreate_InvalidTour_ReturnsNegativeTourId() {
        // Arrange
        TourDaoModel tour = new TourDaoModel(
                "Test",
                "Test",
                "Wien",
                "Linz",
                null,
                100,
                100,
                HillType.AVOID_UP_HILL
        );

        // Act
        Long tourId = tourRepository.create(tour);

        // Assert
        assertEquals(-1L, tourId);
    }

    @Test
    public void testCreate_NullTour_ReturnsNegativeTourId() {
        // Arrange
        TourDaoModel tour = null;

        // Act
        Long tourId = tourRepository.create(tour);

        // Assert
        assertEquals(-1L, tourId);
    }

    @Test
    public void testRead_ExistingTour_ReturnsTourDaoModel() {
        // Arrange
        Long tourId = 1L;

        TourDaoModel expectedTour = mock(TourDaoModel.class);
        Session sessionMock = mock(Session.class);
        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.get(TourDaoModel.class, tourId)).thenReturn(expectedTour);

        // Act
        TourDaoModel result = tourRepository.find(tourId);

        // Assert
        assertEquals(expectedTour, result);
    }

    @Test
    public void testRead_NonExistingTour_ReturnsNull() {
        // Arrange
        Long tourId = 1L;

        Session sessionMock = mock(Session.class);
        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.get(TourDaoModel.class, tourId)).thenReturn(null);

        // Act
        TourDaoModel result = tourRepository.find(tourId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testUpdate_ValidTour_ReturnsTrue() {
        // Arrange
        TourDaoModel tour = mock(TourDaoModel.class, withSettings().useConstructor(
                "Test",
                "Test",
                "Wien",
                "Linz",
                TransportationType.BIKE,
                100,
                100,
                HillType.AVOID_UP_HILL
        ));

        Session sessionMock = mock(Session.class);
        Transaction transactionMock = mock(Transaction.class);
        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.getTransaction()).thenReturn(transactionMock);

        when(tour.getName()).thenCallRealMethod();
        when(tour.getDescription()).thenCallRealMethod();
        when(tour.getDestinationFrom()).thenCallRealMethod();
        when(tour.getDestinationTo()).thenCallRealMethod();
        when(tour.getTransportationType()).thenCallRealMethod();
        when(tour.getDistance()).thenCallRealMethod();
        when(tour.getEstimatedTime()).thenCallRealMethod();
        when(tour.getHillType()).thenCallRealMethod();

        // Act
        boolean result = tourRepository.update(tour);

        // Assert
        assertTrue(result);
        verify(eventAggregator).publish(Event.TOUR_UPDATED);
    }

    @Test
    public void testUpdate_InvalidTour_ReturnsFalse() {
        // Arrange
        TourDaoModel tour = mock(TourDaoModel.class);

        Session sessionMock = mock(Session.class);
        Transaction transactionMock = mock(Transaction.class);
        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.getTransaction()).thenReturn(transactionMock);

        // Act
        boolean result = tourRepository.update(tour);

        // Assert
        assertFalse(result);
        verify(logger).warn("Invalid tour: " + (tour != null ? tour.toString() : "tour is null"));
        verifyNoInteractions(sessionFactory, eventAggregator);
    }

    @Test
    void testDelete_ValidTour_ReturnsTrue() {
        // Arrange
        TourDaoModel tour = mock(TourDaoModel.class);
        Session sessionMock = mock(Session.class);
        Transaction transactionMock = mock(Transaction.class);

        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.getTransaction()).thenReturn(transactionMock);

        // Act
        boolean result = tourRepository.delete(tour);

        // Assert
        assertTrue(result);
        verify(sessionMock).delete(tour);
        verify(transactionMock).commit();
        verify(eventAggregator).publish(Event.TOUR_DELETED);
    }

    @Test
    void testDelete_NullTour_ReturnsFalse() {
        // Act
        boolean result = tourRepository.delete(null);

        // Assert
        assertFalse(result);
        verify(logger).warn("Cannot delete null tour");
    }

    @Test
    void testFindAll_ToursExist_ReturnsListOfTours() {
        // Arrange
        List<TourDaoModel> tours = new ArrayList<>();
        tours.add(mock(TourDaoModel.class));
        tours.add(mock(TourDaoModel.class));

        Session sessionMock = mock(Session.class);
        Query queryMock = mock(Query.class);

        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.createQuery("FROM TourDaoModel", TourDaoModel.class)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(tours);

        // Act
        List<TourDaoModel> result = tourRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(tours.size(), result.size());
        assertEquals(tours.get(0), result.get(0));
        assertEquals(tours.get(1), result.get(1));
    }

    @Test
    void testFindAll_NoToursExist_ReturnsNull() {
        // Arrange
        Session sessionMock = mock(Session.class);
        Query queryMock = mock(Query.class);

        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.createQuery("FROM TourDaoModel", TourDaoModel.class)).thenReturn(queryMock);
        when(queryMock.getResultList()).thenThrow(NoResultException.class);

        // Act
        List<TourDaoModel> result = tourRepository.findAll();

        // Assert
        assertNull(result);
        verify(logger).warn("No tours available in the database");
    }

    @Test
    void testFindAll_ExceptionThrown_ReturnsNull() {
        // Arrange
        Session sessionMock = mock(Session.class);

        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.createQuery("FROM TourDaoModel", TourDaoModel.class)).thenThrow(RuntimeException.class);

        // Act
        List<TourDaoModel> result = tourRepository.findAll();

        // Assert
        assertNull(result);
        verify(logger).error(anyString(), any(RuntimeException.class));
    }

    @Test
    void testFindFirst_TourExists_ReturnsTourDaoModel() {
        // Arrange
        Session sessionMock = mock(Session.class);
        Query queryMock = mock(Query.class);
        TourDaoModel tourMock = mock(TourDaoModel.class);

        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.createQuery("FROM TourDaoModel", TourDaoModel.class)).thenReturn(queryMock);
        when(queryMock.setMaxResults(1)).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(tourMock);

        // Act
        TourDaoModel result = tourRepository.findFirst();

        // Assert
        assertNotNull(result);
        assertEquals(tourMock, result);
        verify(logger, never()).warn(anyString());
        verify(logger, never()).error(anyString(), any(Exception.class));
    }

    @Test
    void testFindFirst_TourExistsWithName_ReturnsTourDaoModel() {
        // Arrange
        String tourName = "TestTour";
        Session sessionMock = mock(Session.class);
        Query queryMock = mock(Query.class);
        TourDaoModel tourMock = mock(TourDaoModel.class);

        when(sessionFactory.openSession()).thenReturn(sessionMock);
        when(sessionMock.createQuery("FROM TourDaoModel WHERE name = :name", TourDaoModel.class)).thenReturn(queryMock);
        when(queryMock.setParameter("name", tourName)).thenReturn(queryMock);
        when(queryMock.setMaxResults(1)).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(tourMock);

        // Act
        TourDaoModel result = tourRepository.findFirst(tourName);

        // Assert
        assertNotNull(result);
        assertEquals(tourMock, result);
        verify(logger, never()).warn(anyString());
        verify(logger, never()).error(anyString(), any(Exception.class));
    }
}
