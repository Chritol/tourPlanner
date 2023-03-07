package at.technikum.tolanzeilinger.tourplanner.viewModel;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.model.WordRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewModel {
    private final WordRepository wordRepository;
    private final EventAggregator eventAggregator;

    private final ObservableList<String> names = FXCollections.observableArrayList();
    private final StringProperty input = new SimpleStringProperty();
    private final StringProperty selectedName = new SimpleStringProperty();

    public MainViewModel(
            EventAggregator eventAggregator,
            WordRepository wordRepository
    ) {
        this.eventAggregator = eventAggregator;
        this.wordRepository = wordRepository;

        initializeView();
        initializeEventListeners();
    }

    private void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.CREATE_WORD, this::updateWords);
        eventAggregator.addSubscriber(Event.DELETE_WORD, this::updateWords);
    }

    private void initializeView() {
        names.addAll(wordRepository.findAll());
    }

    public String getInput() {
        return input.get();
    }

    public void setInput(String input) {
        this.input.set(input);
    }

    public StringProperty inputProperty() {
        return input;
    }

    public String getSelectedName() {
        return selectedName.get();
    }

    public StringProperty selectedNameProperty() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName.set(selectedName);
    }

    private void updateWords() {
        names.clear();
        names.addAll(wordRepository.findAll());
    }

    public ObservableList<String> getNames() {
        return names;
    }

    public void enterNewWord() {
        wordRepository.add(getInput());
        setInput("");
    }

    public void deleteWord() {
        wordRepository.remove(getSelectedName());
    }
}
