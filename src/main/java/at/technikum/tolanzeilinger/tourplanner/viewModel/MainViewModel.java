package at.technikum.tolanzeilinger.tourplanner.viewModel;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.RouteItem;
import at.technikum.tolanzeilinger.tourplanner.persistence.repositories.WordRepository;
import at.technikum.tolanzeilinger.tourplanner.service.implementations.RouteService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.IRouteService;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainViewModel {
    private final WordRepository wordRepository;
    private final EventAggregator eventAggregator;

    private final IRouteService routeService;

    private final Logger log;

    private final ObservableList<String> names = FXCollections.observableArrayList();
    private final StringProperty input = new SimpleStringProperty();
    private final StringProperty selectedName = new SimpleStringProperty();

    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();

    public MainViewModel(
            EventAggregator eventAggregator,
            WordRepository wordRepository,
            Logger logger
    ) {
        this.eventAggregator = eventAggregator;
        this.wordRepository = wordRepository;
        this.log = logger;
        this.routeService = new RouteService();

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
        try {
            RouteItem asd = routeService.loadRouteFromUrl("https://www.mapquestapi.com/directions/v2/route?key=XSqMMjiT0vjeJtxPj22gTLZ2X2LNiDqj&from=Wien&to=Koeln&unit=K", null);
            Image image = routeService.getRouteImage("https://www.mapquestapi.com/staticmap/v5/map?key=XSqMMjiT0vjeJtxPj22gTLZ2X2LNiDqj&session=", asd.getSessionId());
            log.info("Received route with sessionId:"+asd.getSessionId());
            return image;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
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
