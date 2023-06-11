package at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.TourLogComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourLogService;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.ViewModel;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;

public class TourLogListViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final Logger logger;
    private final TourLogService tourLogService;
    private final ObservableList<TourLogItem> tourLogs;

    public TourLogListViewModel(EventAggregator eventAggregator, Logger logger, TourLogService tourLogService) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;
        this.tourLogService = tourLogService;
        this.tourLogs = FXCollections.observableArrayList();

        initializeView();
        initializeEventListeners();
    }

    @Override
    public void initializeView() {
        // Set calculated values for properties here
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.TOUR_CHANGED, this::update);
        // Add event listeners here
    }

    private void update() {
        tourLogs.clear();
        tourLogService.setActiveTourLogIndex(-1);

        List<TourLog> allTourLogs = tourLogService.getAllTourLogsForActiveTour();
        if(allTourLogs != null) {
            for (TourLog tourLog : allTourLogs) {
                tourLogs.add(new TourLogItem(
                        tourLog.getId(),
                        tourLog.getLogDateTime(),
                        tourLog.getComment(),
                        tourLog.getDifficulty().name(),
                        tourLog.getTotalTime().toString(),
                        tourLog.getRating().toString()
                ));
            }
        } else {
            long activeTourLogIndex = tourLogService.getActiveTourLogIndex();
            if(activeTourLogIndex >= 0)
                logger.warn("No logs exist for tour with id: " + activeTourLogIndex);
        }
    }

    public ObservableList<TourLogItem> getTourLogs() {
        return tourLogs;
    }

    public void handleTableItemClick(TourLogItem selectedItem) {
        try {
            tourLogService.setActiveTourLogIndex(selectedItem.getId());
        } catch (NullPointerException e) {
            logger.warn("Received a click that wasn't meant to be processed.");
        }

    }

    public static class TourLogItem {
        private final SimpleLongProperty id;
        private final SimpleObjectProperty<LocalDateTime> dateTime;
        private final SimpleStringProperty comment;
        private final SimpleStringProperty difficulty;
        private final SimpleStringProperty totalTime;
        private final SimpleStringProperty rating;

        public TourLogItem(Long id, LocalDateTime dateTime, String comment, String difficulty, String totalTime, String rating) {
            this.id = new SimpleLongProperty(id);
            this.dateTime = new SimpleObjectProperty<>(dateTime);
            this.comment = new SimpleStringProperty(comment);
            this.difficulty = new SimpleStringProperty(difficulty);
            this.totalTime = new SimpleStringProperty(totalTime);
            this.rating = new SimpleStringProperty(rating);
        }

        public Long getId() {
            return id.get();
        }

        public LocalDateTime getDateTime() {
            return dateTime.get();
        }

        public String getComment() {
            return comment.get();
        }

        public String getDifficulty() {
            return difficulty.get();
        }

        public String getTotalTime() {
            return totalTime.get();
        }

        public String getRating() {
            return rating.get();
        }

        public SimpleLongProperty idProperty() {
            return id;
        }

        public SimpleObjectProperty<LocalDateTime> dateTimeProperty() {
            return dateTime;
        }

        public SimpleStringProperty commentProperty() {
            return comment;
        }

        public SimpleStringProperty difficultyProperty() {
            return difficulty;
        }

        public SimpleStringProperty totalTimeProperty() {
            return totalTime;
        }

        public SimpleStringProperty ratingProperty() {
            return rating;
        }
    }
}
