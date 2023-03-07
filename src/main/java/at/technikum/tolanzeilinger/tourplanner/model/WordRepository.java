package at.technikum.tolanzeilinger.tourplanner.model;

import at.technikum.tolanzeilinger.tourplanner.event.DeleteWordEvent;
import at.technikum.tolanzeilinger.tourplanner.event.NewWordEvent;

import java.util.ArrayList;
import java.util.List;

public class WordRepository {
    private final List<String> words;

    private final List<NewWordEvent> newWordEvents;

    private final List<DeleteWordEvent> deleteWordEvents;

    public WordRepository() {
        words = new ArrayList<>();
        newWordEvents = new ArrayList<>();
        deleteWordEvents = new ArrayList<>();
    }

    public void add(String word) {
        if(word != null && !words.contains(word))
            words.add(word);

        notifyNewWordListeners(word);
    }

    public void remove(String word) {
        if(word != null)
            words.remove(word);

        notifyDeleteWordListeners(word);
    }

    public List<String> findAll() {
        return words;
    }

    public void addNewWordListener(NewWordEvent event) {
        newWordEvents.add(event);
    }

    private void notifyNewWordListeners(String word) {
        for (NewWordEvent event : newWordEvents) {
            event.newWord(word);
        }
    }

    public void addDeleteWordListener(DeleteWordEvent event) {
        deleteWordEvents.add(event);
    }

    private void notifyDeleteWordListeners(String word) {
        for (DeleteWordEvent event : deleteWordEvents) {
            event.deleteWord(word);
        }
    }
}
