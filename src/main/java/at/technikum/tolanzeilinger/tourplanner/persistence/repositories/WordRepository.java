package at.technikum.tolanzeilinger.tourplanner.persistence.repositories;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;

import java.util.ArrayList;
import java.util.List;

public class WordRepository {
    private final List<String> words;
    private final EventAggregator eventAggregator;

    public WordRepository(EventAggregator eventAggregator) {
        this.eventAggregator = eventAggregator;

        words = new ArrayList<>();
    }

    public void add(String object) {
        if(object != null && !words.contains(object))
            words.add(object);

        eventAggregator.publish(Event.CREATE_WORD);
    }

    public void remove(String object) {
        if(object != null)
            words.remove(object);

        eventAggregator.publish(Event.DELETE_WORD);
    }

    public List<String> findAll() {
        return words;
    }

    public String findFirst() {
        return words.get(0);
    }
}
