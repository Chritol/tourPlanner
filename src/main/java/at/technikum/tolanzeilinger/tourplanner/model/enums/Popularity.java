package at.technikum.tolanzeilinger.tourplanner.model.enums;

public enum Popularity {
    NEVER_DONE("Never done", -1),
    OVER_10("More than 10 times", 10),
    OVER_50("More than 10 times", 50),
    OVER_100("More than 10 times", 100);

    private final String textValue;
    private final int numeralValue;

    Popularity(String textValue, int numeralValue) {
        this.textValue = textValue;
        this.numeralValue = numeralValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public int getNumeralValue() {
        return numeralValue;
    }
}
