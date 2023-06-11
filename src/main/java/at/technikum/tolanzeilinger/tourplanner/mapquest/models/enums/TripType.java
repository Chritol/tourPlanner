package at.technikum.tolanzeilinger.tourplanner.mapquest.models.enums;

public enum TripType {
    BIKE("bicycle"),
    CAR("fastest"),
    WALK("pedestrian");

    private final String apiKey;

    TripType(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
