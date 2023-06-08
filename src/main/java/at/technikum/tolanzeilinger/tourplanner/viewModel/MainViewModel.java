package at.technikum.tolanzeilinger.tourplanner.viewModel;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.api.models.TourDtoModel;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.WordRepository;
import at.technikum.tolanzeilinger.tourplanner.service.api.implementations.MapquestServiceImpl;
import at.technikum.tolanzeilinger.tourplanner.service.api.interfaces.MapquestService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.ImageStorageService;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainViewModel {
    private final WordRepository wordRepository;
    private final EventAggregator eventAggregator;

    private final MapquestService mapquestService;

    private final ImageStorageService imageStorageService;

    private final Logger log;

    private final ObservableList<String> names = FXCollections.observableArrayList();
    private final StringProperty input = new SimpleStringProperty();
    private final StringProperty selectedName = new SimpleStringProperty();

    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();

    public MainViewModel(
            EventAggregator eventAggregator,
            WordRepository wordRepository,
            Logger logger,
            MapquestService mapquestService,
            ImageStorageService imageStorageService
    ) {
        this.eventAggregator = eventAggregator;
        this.wordRepository = wordRepository;
        this.log = logger;
        this.mapquestService = mapquestService;
        this.imageStorageService = imageStorageService;

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

    public Image getRouteImage() {
        return null;
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
