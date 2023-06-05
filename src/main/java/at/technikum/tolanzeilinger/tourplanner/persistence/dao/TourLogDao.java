package at.technikum.tolanzeilinger.tourplanner.persistence.dao;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.Difficulty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tp_tour_log")
public class TourLogDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private TourDao tour;

    @Column(name = "log_datetime")
    private LocalDateTime logDateTime;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", length = 50)
    private Difficulty difficulty;

    @Column(name = "total_time")
    private Integer totalTime;

    @Column(name = "rating")
    private Integer rating;

    public TourLogDao(TourDao tour, LocalDateTime logDateTime, String comment, Difficulty difficulty, Integer totalTime, Integer rating) {
        this.tour = tour;
        this.logDateTime = logDateTime;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
    }

    public TourLogDao() {

    }

    public Long getId() {
        return id;
    }

    public TourDao getTour() {
        return tour;
    }

    public LocalDateTime getLogDateTime() {
        return logDateTime;
    }

    public String getComment() {
        return comment;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public Integer getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "TourLogDao{" +
                "id=" + id +
                ", tour=" + tour +
                ", logDateTime=" + logDateTime +
                ", comment='" + comment + '\'' +
                ", difficulty=" + difficulty +
                ", totalTime='" + totalTime + '\'' +
                ", rating=" + rating +
                '}';
    }
}
