package at.technikum.tolanzeilinger.tourplanner.dialogs;

import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers.DeleteConfirmationDialogWrapper;
import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers.LogCUDialogWrapper;
import at.technikum.tolanzeilinger.tourplanner.dialogs.ResultSets.LogCUDialogResult;
import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.TourConverter;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourLogService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;

/**
 * Single dispatcher for all dialogs
 */
public class DialogService {
    private final Logger logger;
    private final EventAggregator eventAggregator;
    private final DialogFactory dialogFactory;

    private final TourService tourService;
    private final TourLogService tourLogService;

    public DialogService(Logger logger, EventAggregator eventAggregator, TourService tourService, TourLogService tourLogService){
        this.logger = logger;
        this.eventAggregator = eventAggregator;
        this.tourService = tourService;
        this.tourLogService = tourLogService;

        this.dialogFactory = new DialogFactory();

        initializeEventListeners();
    }

    private void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.REMOVE_TOUR_ACTION, this::removeTourForm);

        eventAggregator.addSubscriber(Event.NEW_LOG_ACTION, this::newLogForm);
        eventAggregator.addSubscriber(Event.EDIT_LOG_ACTION, this::updateLogForm);
        eventAggregator.addSubscriber(Event.REMOVE_LOG_ACTION, this::removeLogForm);
    }

    private void updateLogForm() {
    }

    private void removeLogForm() {
    }

    private void removeTourForm() {
        Tour activeTour = tourService.getActiveTour();
        DeleteConfirmationDialogWrapper dialogWrapper = (DeleteConfirmationDialogWrapper) dialogFactory.createDialog(
                DialogType.DELETE_CONFIRMATION,
                "Delete tour \""+ activeTour.getName() + "\"",
                "Do you want to delete the tour \"" + activeTour.getName() + "\" forever?"
        );

        Boolean result = dialogWrapper.showAndReturn();

        if (result) {
            logger.info("Confirmation given for removing tour \"" + activeTour.getName() + "\"");
            tourService.deleteTourByIndex(activeTour.getId());
        } else {
            logger.info("Deletion cancelled");
        }

    }

    private void newLogForm() {
        LogCUDialogWrapper dialogWrapper = (LogCUDialogWrapper) dialogFactory.createDialog(
                DialogType.LOG_CREATE_UPDATE,
                "New Log",
                "Create a new Log for the selected tour"
        );

        LogCUDialogResult results = dialogWrapper.showAndReturn();

        if (results!= null) {
            TourLog newLog = new TourLog();

            /**
            newLog.setComment(results.getComment());
            newLog.setDifficulty(results.getDifficulty());
            newLog.setLogDateTime(results.getDateTime());

            newLog.setTour(
                    TourConverter.toTourDaoModel(tourService.getActiveTour())
            );
            */

            tourLogService.addLog(newLog);
        }
    }
}
