package at.technikum.tolanzeilinger.tourplanner.persistence.dao.models;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillTypeDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.TransportationTypeDaoEnum;
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
    private TransportationTypeDaoEnum transportationTypeDaoEnum;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "estimated_time")
    private Integer estimatedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "hill_type", length = 50)
    private HillTypeDaoEnum hillTypeDaoEnum;

    public TourDaoModel() {
    }

    public TourDaoModel(String name, String description, String destinationFrom, String destinationTo, TransportationTypeDaoEnum transportationTypeDaoEnum, Integer distance, Integer estimatedTime, HillTypeDaoEnum hillTypeDaoEnum) {
        this.name = name;
        this.description = description;
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.transportationTypeDaoEnum = transportationTypeDaoEnum;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.hillTypeDaoEnum = hillTypeDaoEnum;
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

    public TransportationTypeDaoEnum getTransportationType() {
        return transportationTypeDaoEnum;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public HillTypeDaoEnum getHillType() {
        return hillTypeDaoEnum;
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
                ", transportationType=" + transportationTypeDaoEnum +
                ", distance=" + distance +
                ", estimatedTime=" + estimatedTime +
                ", hillType=" + hillTypeDaoEnum +
                '}';
    }
}
