package at.technikum.tolanzeilinger.tourplanner.model.tours;

public class Tour {
    private String name;
    private String description;
    private Location from;
    private Location to;
    private Transportation transportation;
    private Hilltype hilliness;
    private int distance;
    private int estimatedTime;

    String tourImagePath;

    public Tour(String name, String description, String from, String to){
        this.name = name;
        this.description = description;
        this.from = new Location(from);
        this.to = new Location(to);
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

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
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
}
