package at.technikum.tolanzeilinger.tourplanner.dialogs;

import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers.DialogWrapper;
import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers.GenericDialogWrapper;
import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers.LogCUDialogWrapper;

public class DialogFactory {

    public DialogWrapper createDialog(DialogType dialogType, String title, String message) {
        DialogWrapper dialogWrapper = createDialog(dialogType, title);

        dialogWrapper.setMessage(message);

        return dialogWrapper;
    }

    public DialogWrapper createDialog(DialogType dialogType, String title) {
        DialogWrapper dialogWrapper = createDialog(dialogType);

        dialogWrapper.setTitle(title);

        return dialogWrapper;
    }

    public DialogWrapper createDialog(DialogType dialogType) {
        if (dialogType == DialogType.LOG_DELETE) {

        }
        if (dialogType == DialogType.LOG_CREATE_UPDATE) {
            return new LogCUDialogWrapper();
        }
        return new GenericDialogWrapper();
    }
}
