package at.technikum.tolanzeilinger.tourplanner.dialogs;

import at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers.*;

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
        if (dialogType == DialogType.INFO) {
            return new InfoDialogWrapper();
        }
        if (dialogType == DialogType.DELETE_CONFIRMATION) {
            return new DeleteConfirmationDialogWrapper();
        }
        if (dialogType == DialogType.LOG_CREATE_UPDATE) {
            return new LogCUDialogWrapper();
        }

        return new GenericDialogWrapper();
    }
}
