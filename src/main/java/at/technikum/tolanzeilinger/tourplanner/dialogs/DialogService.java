package at.technikum.tolanzeilinger.tourplanner.dialogs;

import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers.LogCUDialogWrapper;
import at.technikum.tolanzeilinger.tourplanner.dialogs.ResultSets.LogCUDialogResult;
import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;

/**
 * Single dispatcher for all dialogs
 */
public class DialogService {
    private final Logger logger;
    private final EventAggregator eventAggregator;
    private final DialogFactory dialogFactory;

    public DialogService(Logger logger, EventAggregator eventAggregator){
        this.logger = logger;
        this.eventAggregator = eventAggregator;

        this.dialogFactory = new DialogFactory();

        initializeEventListeners();
    }

    private void initializeEventListeners() {

        eventAggregator.addSubscriber(Event.NEW_LOG_ACTION, this::newLogForm);
    }

    private void newLogForm() {
        LogCUDialogWrapper dialogWrapper = (LogCUDialogWrapper) dialogFactory.createDialog(
                DialogType.LOG_CREATE_UPDATE,
                "New Log",
                "Create a new Log for the selected tour"
        );

        LogCUDialogResult results = dialogWrapper.showAndReturn().get();

        System.out.println(results.getDistance());
    }
}
