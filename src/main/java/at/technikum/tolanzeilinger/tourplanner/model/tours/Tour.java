package at.technikum.tolanzeilinger.tourplanner.model.tours;

public class Tour {
    String name;
    String description;
    String from;
    String to;
    Transportation transportation;
    Hilltype hilliness;
    int distance;
    int estimatedTime;

    String tourImagePath;

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
}
