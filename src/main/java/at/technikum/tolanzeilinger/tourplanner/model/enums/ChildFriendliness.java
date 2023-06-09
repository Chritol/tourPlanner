package at.technikum.tolanzeilinger.tourplanner.model.enums;

public enum ChildFriendliness {
    NOT_FOR_CHILDREN("Not ment for children", 3, 100, 10),
    NOT_RECOMMENDED_FOR_CHILDREN("Not recommended for children", 6, 150, 25),
    RECOMMENDED_FOR_CHILDREN("Recommended for children", 10, 1000, 1000);
    private final String textValue;
    private final int maxRating;
    private final int maxTime;
    private final int maxDistance;

    ChildFriendliness(String textValue, int maxRating, int maxTime, int maxDistance) {
        this.textValue = textValue;
        this.maxRating = maxRating;
        this.maxTime = maxTime;
        this.maxDistance = maxDistance;
    }

    public String getTextValue() {
        return textValue;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public int getMaxDistance() {
        return maxDistance;
    }
}
