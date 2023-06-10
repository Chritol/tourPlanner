package at.technikum.tolanzeilinger.tourplanner.persistence.dao.models;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.DifficultyDaoEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tp_tour_log")
public class TourLogDaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private TourDaoModel tour;

    @Column(name = "log_datetime")
    private LocalDateTime logDateTime;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", length = 50)
    private DifficultyDaoEnum difficultyDaoEnum;

    @Column(name = "total_time")
    private Integer totalTime;

    @Column(name = "rating")
    private Integer rating;

    public TourLogDaoModel(TourDaoModel tour, LocalDateTime logDateTime, String comment, DifficultyDaoEnum difficultyDaoEnum, Integer totalTime, Integer rating) {
        this.tour = tour;
        this.logDateTime = logDateTime;
        this.comment = comment;
        this.difficultyDaoEnum = difficultyDaoEnum;
        this.totalTime = totalTime;
        this.rating = rating;
    }

    public TourLogDaoModel() {

    }

    public Long getId() {
        return id;
    }

    public TourDaoModel getTour() {
        return tour;
    }

    public LocalDateTime getLogDateTime() {
        return logDateTime;
    }

    public String getComment() {
        return comment;
    }

    public DifficultyDaoEnum getDifficulty() {
        return difficultyDaoEnum;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public Integer getRating() {
        return rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TourLogDao{" +
                "id=" + id +
                ", tour=" + tour +
                ", logDateTime=" + logDateTime +
                ", comment='" + comment + '\'' +
                ", difficulty=" + difficultyDaoEnum +
                ", totalTime='" + totalTime + '\'' +
                ", rating=" + rating +
                '}';
    }
}
