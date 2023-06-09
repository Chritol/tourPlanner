package at.technikum.tolanzeilinger.tourplanner.dialogs.DialogWrappers;

import at.technikum.tolanzeilinger.tourplanner.constants.StylingConstants;
import at.technikum.tolanzeilinger.tourplanner.dialogs.ResultSets.LogCUDialogResult;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.Difficulty;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static at.technikum.tolanzeilinger.tourplanner.util.StringUtilities.*;

public class LogCUDialogWrapper implements DialogWrapper<LogCUDialogResult> {

    private final Dialog<LogCUDialogResult> dialog;

    private final GridPane grid;
    private final DatePicker datePicker;
    private final TextField timePicker;
    private final TextField commentTextField;
    private final ChoiceBox<Difficulty> difficultyChoiceBox;
    private final TextField totalTimeTextField;
    private final TextField ratingTextField;

    public LogCUDialogWrapper() {
        this.dialog = new Dialog<>();

        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.FINISH,
                ButtonType.CANCEL
        );

        this.grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        this.datePicker = new DatePicker();
        this.timePicker = new TextField("HH:mm");
        this.commentTextField = new TextField();
        this.difficultyChoiceBox = new ChoiceBox<>();
        this.totalTimeTextField = new TextField();
        this.ratingTextField = new TextField();

        // Set the initial date to today
        LocalDateTime today = LocalDateTime.now();
        this.datePicker.setValue(today.toLocalDate());
        this.timePicker.setText(today.format(DateTimeFormatter.ofPattern("HH:mm")));

        // Populate the difficulty choice box with enum values
        difficultyChoiceBox.getItems().addAll(Difficulty.values());

        grid.add(new Label("Date:"), 0, 0);
        grid.add(datePicker, 1, 0);
        grid.add(new Label("Time:"), 0, 1);
        grid.add(timePicker, 1, 1);
        grid.add(new Label("Comment:"), 0, 2);
        grid.add(commentTextField, 1, 2);
        grid.add(new Label("Difficulty:"), 0, 3);
        grid.add(difficultyChoiceBox, 1, 3);
        grid.add(new Label("Total Time:"), 0, 4);
        grid.add(totalTimeTextField, 1, 4);
        grid.add(new Label("Rating:"), 0, 5);
        grid.add(ratingTextField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the date picker
        Platform.runLater(() -> datePicker.requestFocus());

        // Convert the result to a LogCUDialogResult when the user closes the dialog
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.FINISH) {
                LocalDate date = datePicker.getValue();
                String time = timePicker.getText();
                LocalDateTime dateTime = null;


                String comment = commentTextField.getText();
                Difficulty difficulty = difficultyChoiceBox.getValue();
                String totalTime = totalTimeTextField.getText();
                String rating = ratingTextField.getText();

                // Validate inputs
                boolean isValid = true;

                datePicker.setBorder(StylingConstants.NORMAL_BORDER);
                timePicker.setBorder(StylingConstants.NORMAL_BORDER);

                try {
                    dateTime = parseDateTime(date, time);
                } catch (Exception e) {
                    datePicker.setBorder(StylingConstants.ERROR_BORDER);
                    timePicker.setBorder(StylingConstants.ERROR_BORDER);
                    isValid = false;
                }
                if(dateTime == null) {
                    datePicker.setBorder(StylingConstants.ERROR_BORDER);
                    timePicker.setBorder(StylingConstants.ERROR_BORDER);
                    isValid = false;
                }

                commentTextField.setBorder(StylingConstants.NORMAL_BORDER);
                if (isNullOrWhitespace(comment) || isTextTooLong(comment, 50)) {
                    commentTextField.setBorder(StylingConstants.ERROR_BORDER);
                    isValid = false;
                }

                difficultyChoiceBox.setBorder(StylingConstants.NORMAL_BORDER);
                if (difficulty == null) {
                    difficultyChoiceBox.setBorder(StylingConstants.ERROR_BORDER);
                    isValid = false;
                }

                totalTimeTextField.setBorder(StylingConstants.NORMAL_BORDER);
                if (isNullOrWhitespace(totalTime) || !totalTime.matches("\\d+")) {
                    totalTimeTextField.setBorder(StylingConstants.ERROR_BORDER);
                    isValid = false;
                }

                ratingTextField.setBorder(StylingConstants.NORMAL_BORDER);
                if (isNullOrWhitespace(rating) || !rating.matches("\\d+")) {
                    ratingTextField.setBorder(StylingConstants.ERROR_BORDER);
                    isValid = false;
                }

                if (isValid) {
                    return new LogCUDialogResult(
                            dateTime,
                            clearTrailingWhitespaces(comment),
                            difficulty,
                            totalTime,
                            rating
                    ); // Return null if any input is invalid
                }


            } else {
                return null;
            }
            throw new IllegalStateException("This is meant to happen");
        });
    }

    private LocalDateTime parseDateTime(LocalDate date, String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalDateTime.of(date, LocalTime.parse(time, timeFormatter));
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
    public void setOptions(LogCUDialogResult options) {
        LocalDateTime dateTime = options.getDateTime();
        this.datePicker.setValue(
                dateTime.toLocalDate()
        );
        this.timePicker.setText(
                dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        );
        this.commentTextField.setText(
                options.getComment()
        );
        this.difficultyChoiceBox.setValue(
                options.getDifficulty()
        );
        this.totalTimeTextField.setText(
                options.getTotalTime()
        );
        this.ratingTextField.setText(
                options.getRating()
        );
    }

    @Override
    public void show() {
        dialog.showAndWait();
    }

    @Override
    public LogCUDialogResult showAndReturn() {
        var result = dialog.showAndWait();
        return result.orElse(null);
    }
}
