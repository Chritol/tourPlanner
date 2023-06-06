package at.technikum.tolanzeilinger.tourplanner.model.repositories;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface Repository<T> {

    void add(T word);

    void remove(T word);

    List<T> findAll();

    T findFirst(

    );
}
