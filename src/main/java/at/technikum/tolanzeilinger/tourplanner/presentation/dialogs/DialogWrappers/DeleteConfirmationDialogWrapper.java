package at.technikum.tolanzeilinger.tourplanner.presentation.dialogs.DialogWrappers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DeleteConfirmationDialogWrapper implements DialogWrapper<Boolean> {
    Alert dialog;

    public DeleteConfirmationDialogWrapper(){
        this.dialog = new Alert(Alert.AlertType.CONFIRMATION);
    }

    @Override
    public void setTitle(String title) {
        dialog.setTitle(title);
    }

    @Override
    public void setMessage(String message) {
        dialog.setContentText(message);
    }

    @Override
    public void setOptions(Boolean options) {
        //This Dialog does not have options
    }

    @Override
    public void show() {
        dialog.showAndWait();
    }

    @Override
    public Boolean showAndReturn() {
        Optional<ButtonType> result = dialog.showAndWait();
        return result.get() == ButtonType.OK;
    }
}
