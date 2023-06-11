package at.technikum.tolanzeilinger.tourplanner.mapquest.models;

public enum RoadStrategy {
    DEFAULT_STRATEGY("DEFAULT_STRATEGY"),
    AVOID_UP_HILL("AVOID_UP_HILL"),
    AVOID_DOWN_HILL("AVOID_DOWN_HILL"),
    AVOID_ALL_HILLS("AVOID_ALL_HILLS"),
    FAVOR_UP_HILL("FAVOR_UP_HILL"),
    FAVOR_DOWN_HILL("FAVOR_DOWN_HILL"),
    FAVOR_ALL_HILLS("FAVOR_ALL_HILLS");

    private final String apiKey;

    RoadStrategy(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
