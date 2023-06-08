package at.technikum.tolanzeilinger.tourplanner.model;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.Difficulty;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.models.TourDaoModel;

import java.time.LocalDateTime;

public class TourLog {
    private Long id;
    private TourDaoModel tour;
    private LocalDateTime logDateTime;
    private String comment;
    private Difficulty difficulty;
    private Integer totalTime;
    private Integer rating;

    public TourLog(Long id, TourDaoModel tour, LocalDateTime logDateTime, String comment, Difficulty difficulty, Integer totalTime, Integer rating) {
        this.id = id;
        this.tour = tour;
        this.logDateTime = logDateTime;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }

    public TourLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TourDaoModel getTour() {
        return tour;
    }

    public void setTour(TourDaoModel tour) {
        this.tour = tour;
    }

    public LocalDateTime getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(LocalDateTime logDateTime) {
        this.logDateTime = logDateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
