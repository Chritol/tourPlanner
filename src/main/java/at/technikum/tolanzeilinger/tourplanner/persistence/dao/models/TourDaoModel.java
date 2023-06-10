package at.technikum.tolanzeilinger.tourplanner.persistence.dao.models;

import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.ChildFriendlinessDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.HillTypeDaoEnum;
import at.technikum.tolanzeilinger.tourplanner.persistence.dao.enums.PopularityDaoEnum;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "popularity", length = 50)
    private PopularityDaoEnum popularityDaoEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "child_friendliness", length = 50)
    private ChildFriendlinessDaoEnum childFriendlinessDaoEnum;

    public TourDaoModel() {
    }

    public TourDaoModel(
            String name,
            String description,
            String destinationFrom,
            String destinationTo,
            TransportationTypeDaoEnum transportationTypeDaoEnum,
            Integer distance,
            Integer estimatedTime,
            HillTypeDaoEnum hillTypeDaoEnum,
            PopularityDaoEnum popularityDaoEnum,
            ChildFriendlinessDaoEnum childFriendlinessDaoEnum) {
        this.name = name;
        this.description = description;
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.transportationTypeDaoEnum = transportationTypeDaoEnum;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.hillTypeDaoEnum = hillTypeDaoEnum;
        this.popularityDaoEnum = popularityDaoEnum;
        this.childFriendlinessDaoEnum = childFriendlinessDaoEnum;
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

    public PopularityDaoEnum getPopularityDaoEnum() {
        return popularityDaoEnum;
    }

    public void setPopularityDaoEnum(PopularityDaoEnum popularityDaoEnum) {
        this.popularityDaoEnum = popularityDaoEnum;
    }

    public ChildFriendlinessDaoEnum getChildFriendlinessDaoEnum() {
        return childFriendlinessDaoEnum;
    }

    public void setChildFriendlinessDaoEnum(ChildFriendlinessDaoEnum childFriendlinessDaoEnum) {
        this.childFriendlinessDaoEnum = childFriendlinessDaoEnum;
    }

    @Override
    public String toString() {
        return "TourDaoModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", destinationFrom='" + destinationFrom + '\'' +
                ", destinationTo='" + destinationTo + '\'' +
                ", transportationTypeDaoEnum=" + transportationTypeDaoEnum +
                ", distance=" + distance +
                ", estimatedTime=" + estimatedTime +
                ", hillTypeDaoEnum=" + hillTypeDaoEnum +
                ", popularityDaoEnum=" + popularityDaoEnum +
                ", childFriendlinessDaoEnum=" + childFriendlinessDaoEnum +
                '}';
    }
}
