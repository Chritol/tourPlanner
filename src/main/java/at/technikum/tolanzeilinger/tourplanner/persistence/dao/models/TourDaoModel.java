package at.technikum.tolanzeilinger.tourplanner.persistence.dao.models;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationType;
import jakarta.persistence.*;

@Entity
@Table(name = "tp_tour")
public class TourDaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "destination_from", length = 100)
    private String destinationFrom;

    @Column(name = "destination_to", length = 100)
    private String destinationTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "transportation_type", length = 50)
    private TransportationType transportationType;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "estimated_time")
    private Integer estimatedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "hill_type", length = 50)
    private HillType hillType;

    public TourDaoModel() {
    }

    public TourDaoModel(String name, String description, String destinationFrom, String destinationTo, TransportationType transportationType, Integer distance, Integer estimatedTime, HillType hillType) {
        this.name = name;
        this.description = description;
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.transportationType = transportationType;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.hillType = hillType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDestinationFrom() {
        return destinationFrom;
    }

    public String getDestinationTo() {
        return destinationTo;
    }

    public TransportationType getTransportationType() {
        return transportationType;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public HillType getHillType() {
        return hillType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TourDao{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", destinationFrom='" + destinationFrom + '\'' +
                ", destinationTo='" + destinationTo + '\'' +
                ", transportationType=" + transportationType +
                ", distance=" + distance +
                ", estimatedTime=" + estimatedTime +
                ", hillType=" + hillType +
                '}';
    }
}
