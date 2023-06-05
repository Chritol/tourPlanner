package at.technikum.tolanzeilinger.tourplanner.persistence;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.TourDao;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.TourLogDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    SessionFactory sessionFactory;

    public HibernateSessionFactory() {
        init();
    }

    private void init() {
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();

        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }
}
