package at.technikum.tolanzeilinger.tourplanner.dialogs.ResultSets;

import at.technikum.tolanzeilinger.tourplanner.model.Difficulty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LogCUDialogResult {
    private final LocalDateTime dateTime;
    private final String comment;
    private final Difficulty difficulty;
    private final String totalTime;
    private final String rating;

    public LogCUDialogResult(LocalDateTime dateTime, String comment, Difficulty difficulty, String totalTime, String rating) {
        this.dateTime = dateTime;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getComment() {
        return comment;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getRating() {
        return rating;
    }
}
