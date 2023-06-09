package at.technikum.tolanzeilinger.tourplanner.model.enums;

public enum Popularity {
    NEVER_DONE("Never done", -1),

    FEW_TIMES("A few times", 0),
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

    public static Popularity getPopularity(int logCount) {
        if(logCount > OVER_100.numeralValue) return OVER_100;
        if(logCount > OVER_50.numeralValue) return OVER_50;
        if(logCount > OVER_10.numeralValue) return OVER_10;
        if(logCount > FEW_TIMES.numeralValue) return FEW_TIMES;
        else return NEVER_DONE;
    }
}
