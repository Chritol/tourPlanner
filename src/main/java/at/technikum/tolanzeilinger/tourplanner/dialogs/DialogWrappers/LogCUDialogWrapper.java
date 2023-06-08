package at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers;

import at.technikum.tolanzeilinger.tourplanner.dialogs.ResultSets.LogCUDialogResult;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class LogCUDialogWrapper implements DialogWrapper {

    Dialog<LogCUDialogResult> dialog;

    GridPane grid;
    DatePicker datePicker;
    TextField durationTextField;
    TextField distanceTextField;


    public LogCUDialogWrapper() {
        this.dialog = new Dialog<LogCUDialogResult>();

        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.FINISH,
                ButtonType.CANCEL
        );

        this.grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        this.datePicker = new DatePicker();
        this.distanceTextField = new TextField();
        this.durationTextField = new TextField();

        grid.add(new Label("Date:"), 0, 0);
        grid.add(datePicker, 1, 0);
        grid.add(new Label("Duration:"), 0, 1);
        grid.add(durationTextField, 1, 1);
        grid.add(new Label("Distance:"), 0, 2);
        grid.add(distanceTextField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the date picker
        Platform.runLater(() -> datePicker.requestFocus());

        // Convert the result to a FormResult when the user closes the dialog
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.FINISH) {
                return new LogCUDialogResult(datePicker.getValue(), durationTextField.getText(), distanceTextField.getText());
            } else {
                return null;
            }
        });

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

    public Optional<LogCUDialogResult> showAndReturn() {
        return dialog.showAndWait();
    }
}
