package at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class GenericDialogWrapper implements DialogWrapper<Boolean>{

    Alert dialog;

    public GenericDialogWrapper(){
        this.dialog = new Alert(Alert.AlertType.INFORMATION);
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
    public void setOptions(Object options) {
        //This Dialog does not have options
    }

    @Override
    public void show() {
        dialog.showAndWait();
    }

    @Override
    public Boolean showAndReturn() {
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
}
