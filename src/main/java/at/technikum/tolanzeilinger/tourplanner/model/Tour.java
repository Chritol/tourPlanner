package at.technikum.tolanzeilinger.tourplanner.model;

public class Tour {

    private Long id;
    private String name;
    private String description;
    private String from;
    private String to;
    private Transportation transportation;
    private Hilltype hilliness;
    private int distance;
    private int estimatedTime;

    public Tour(String name, String description, String from, String to){
        this.name = name;
        this.description = description;
        this.from = new String(from);
        this.to = new String(to);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public Hilltype getHilliness() {
        return hilliness;
    }

    public void setHilliness(Hilltype hilliness) {
        this.hilliness = hilliness;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
