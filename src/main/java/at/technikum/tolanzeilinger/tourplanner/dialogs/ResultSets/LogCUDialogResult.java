package at.technikum.tolanzeilinger.tourplanner.dialogs.ResultSets;

import java.time.LocalDate;

public class LogCUDialogResult {
    private final LocalDate date;
    private final String duration;
    private final String distance;

    public LogCUDialogResult(LocalDate date, String duration, String distance) {
        this.date = date;
        this.duration = duration;
        this.distance = distance;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getDistance() {
        return distance;
    }
}
