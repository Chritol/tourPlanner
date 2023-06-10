package at.technikum.tolanzeilinger.tourplanner.dialogs;

import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers.DeleteConfirmationDialogWrapper;
import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers.InfoDialogWrapper;
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

import java.time.format.DateTimeFormatter;
import java.util.List;

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

        eventAggregator.addSubscriber(Event.OPEN_HELP_ACTION, this::helpForm);

        eventAggregator.addSubscriber(Event.COULD_NOT_CREATE_PDF, this::couldNotCreatePDFForm);
    }

    private void couldNotCreatePDFForm() {
        InfoDialogWrapper dialogWrapper = (InfoDialogWrapper) dialogFactory.createDialog(
                DialogType.INFO,
                "PDF could not be created",
                "You must pick a tour to create a PDF!"
        );

        dialogWrapper.show();
        return;
    }


    private void updateLogForm() {
        if (tourLogService.getActiveTourLogIndex() < 0) {
            logger.warn("No active tour log");

            InfoDialogWrapper dialogWrapper = (InfoDialogWrapper) dialogFactory.createDialog(
                    DialogType.INFO,
                    "No active tour log",
                    "Pick a tour log to delete from the bottom panel!"
            );

            dialogWrapper.show();
            return;
        }

        TourLog activeTourLog = tourLogService.getActiveTourLog();
        LogCUDialogWrapper dialogWrapper = (LogCUDialogWrapper) dialogFactory.createDialog(
                DialogType.LOG_CREATE_UPDATE,
                "Delete tour log \""+ activeTourLog.getLogDateTime().format(DateTimeFormatter.ISO_DATE_TIME) + "\"",
                "Do you want to delete the tour log from the " + activeTourLog.getLogDateTime().format(DateTimeFormatter.ISO_DATE_TIME) + " forever?"
        );

        dialogWrapper.setOptions(
                new LogCUDialogResult(
                        activeTourLog.getLogDateTime(),
                        activeTourLog.getComment(),
                        activeTourLog.getDifficulty(),
                        ""+activeTourLog.getRating(),
                        ""+activeTourLog.getTotalTime()
                )
        );

        LogCUDialogResult results = dialogWrapper.showAndReturn();

        if (results!= null) {
            TourLog newLog = new TourLog();

            newLog.setId(activeTourLog.getId());

            newLog.setLogDateTime(results.getDateTime());
            newLog.setDifficulty(results.getDifficulty());
            newLog.setComment(results.getComment());
            newLog.setRating( Integer.parseInt(results.getRating()));
            newLog.setTotalTime( Integer.parseInt(results.getTotalTime()));

            newLog.setTour(
                    tourService.getActiveTour()
            );

            tourLogService.editLog(newLog);
        }
        else {
            logger.info("Log creation cancelled");
        }

    }

    private void removeLogForm() {
        if (tourLogService.getActiveTourLogIndex() < 0) {
            logger.warn("No active tour log");

            InfoDialogWrapper dialogWrapper = (InfoDialogWrapper) dialogFactory.createDialog(
                    DialogType.INFO,
                    "No active tour log",
                    "Pick a tour log to delete from the bottom panel!"
            );

            dialogWrapper.show();
            return;
        }

        TourLog activeTourLog = tourLogService.getActiveTourLog();
        DeleteConfirmationDialogWrapper dialogWrapper = (DeleteConfirmationDialogWrapper) dialogFactory.createDialog(
                DialogType.DELETE_CONFIRMATION,
                "Delete tour log \""+ activeTourLog.getLogDateTime().format(DateTimeFormatter.ISO_DATE_TIME) + "\"",
                "Do you want to delete the tour log from the " + activeTourLog.getLogDateTime().format(DateTimeFormatter.ISO_DATE_TIME) + " forever?"
        );

        Boolean result = dialogWrapper.showAndReturn();

        if (result) {
            logger.info("Confirmation given for removing tour log \"" + activeTourLog.getId() + "\"");
            tourLogService.deleteLog(activeTourLog);
        } else {
            logger.info("Deletion cancelled");
        }
    }

    private void removeTourForm() {
        if (tourService.getActiveTourIndex() < 0) {
            logger.warn("No active tour");

            InfoDialogWrapper dialogWrapper = (InfoDialogWrapper) dialogFactory.createDialog(
                    DialogType.INFO,
                    "No active tour",
                    "Pick a tour first you fucking imbecile!"
            );

            dialogWrapper.show();
            return;
        }

        Tour activeTour = tourService.getActiveTour();
        List<TourLog> logs = tourLogService.getAllTourLogsForActiveTour();

        DeleteConfirmationDialogWrapper dialogWrapper = (DeleteConfirmationDialogWrapper) dialogFactory.createDialog(
                DialogType.DELETE_CONFIRMATION,
                "Delete tour \""+ activeTour.getName() + "\"",
                "Do you want to delete the tour \"" + activeTour.getName() + "\" forever?\n"+
                        "( This action will also delete all logs for this tour! )"
        );

        Boolean result = dialogWrapper.showAndReturn();

        if (result) {
            logger.info("Confirmation given for removing tour \"" + activeTour.getName() + "\"");

            for (TourLog log : logs) {
                tourLogService.deleteLog(log);
            }

            tourService.deleteTourByIndex(activeTour.getId());
        } else {
            logger.info("Deletion cancelled");
        }

    }

    private void newLogForm() {
        if (tourService.getActiveTourIndex() < 0) {
            logger.warn("No active tour");

            InfoDialogWrapper dialogWrapper = (InfoDialogWrapper) dialogFactory.createDialog(
                    DialogType.INFO,
                    "No active tour",
                    "Pick a tour first you fucking imbecile!"
            );

            dialogWrapper.show();
            return;
        }

        LogCUDialogWrapper dialogWrapper = (LogCUDialogWrapper) dialogFactory.createDialog(
                DialogType.LOG_CREATE_UPDATE,
                "New Log for "+ tourService.getActiveTour().getName(),
                "Create a new Log for the tour \"" + tourService.getActiveTour().getName() + "\""
        );

        LogCUDialogResult results = dialogWrapper.showAndReturn();

        if (results!= null) {
            TourLog newLog = new TourLog();

            newLog.setLogDateTime(results.getDateTime());
            newLog.setDifficulty(results.getDifficulty());
            newLog.setComment(results.getComment());
            newLog.setRating( Integer.parseInt(results.getRating()));
            newLog.setTotalTime( Integer.parseInt(results.getTotalTime()));

            newLog.setTour(
                    tourService.getActiveTour()
            );

            tourLogService.addLog(newLog);
        }
        else {
            logger.info("Log creation cancelled");
        }
    }

    private void helpForm() {
        InfoDialogWrapper dialogWrapper = (InfoDialogWrapper) dialogFactory.createDialog(
                DialogType.INFO,
                "Help",
                "Welcome to the Tolanzeilinger Tour Planner!\n" +
                        "Find tours in the List on the left. Add a new tour to the list by clicking the plus sign.\n" +
                        "Find reviews to the tours on the bottom. Add a new review to the list by clicking the plus sign.\n" +
                        "Export as PDF or Excel in the \"misc\" tab."
        );

        dialogWrapper.show();
    }

}
