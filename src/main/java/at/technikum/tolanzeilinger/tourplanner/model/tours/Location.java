package at.technikum.tolanzeilinger.tourplanner.model.tours;

public class Location {
    private final String name;

    public Location(String name) {
        this.name = name;
    }

    public String getLocationName() {
        return name;
    }
}
