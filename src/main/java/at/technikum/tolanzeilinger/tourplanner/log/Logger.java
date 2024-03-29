package at.technikum.tolanzeilinger.tourplanner.log;

public interface Logger {
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
    void error(String message, Throwable throwable);
    void fatal(String message);
    void fatal(String message, Throwable throwable);
}
