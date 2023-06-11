package at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourLogService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.List;

public class LogLineChartViewModel implements ViewModel {
    private final TourLogService tourLogService;
    private final TourService tourService;
    private final EventAggregator eventAggregator;
    private final ListProperty<Integer> totalTime = new SimpleListProperty<>();
    private final StringProperty activeTourName = new SimpleStringProperty();

    public LogLineChartViewModel(TourLogService tourLogService, EventAggregator eventAggregator, TourService tourService) {
        this.tourLogService = tourLogService;
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;

        initializeEventListeners();
    }

    @Override
    public void initializeView() {
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.TOUR_CHANGED, this::updateTotalTimes);
    }

    private void updateTotalTimes () {
        if (tourService.getActiveTourIndex() >= 0) {
            activeTourName.set(tourService.getActiveTour().getName());
            List<TourLog> currentLogs = tourLogService.getAllTourLogsForActiveTour();

            ObservableList<Integer> totalTimeList
                    = FXCollections
                    .observableArrayList(
                            currentLogs
                                    .stream()
                                    .sorted(Comparator.comparing(TourLog::getLogDateTime))
                                    .map(it -> it.getTotalTime())
                                    .toList());
            totalTime.set(totalTimeList);
        } else {
            totalTime.set(FXCollections.observableArrayList());
        }
    }

    public ListProperty<Integer> totalTimeProperty() {
        return totalTime;
    }

    public String getActiveTourName() {
        return activeTourName.get();
    }
}
