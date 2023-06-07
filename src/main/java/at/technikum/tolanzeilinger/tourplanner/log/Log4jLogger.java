package at.technikum.tolanzeilinger.tourplanner.log;

import at.technikum.tolanzeilinger.tourplanner.App;
import org.apache.logging.log4j.LogManager;

public final class Log4jLogger implements Logger {
    private final org.apache.logging.log4j.Logger log;

    private static Log4jLogger instance;

    // TODO change to singleton
    private Log4jLogger() {
        log = LogManager.getLogger(App.class);
    }

    public static Log4jLogger getInstance(){
        if(instance == null)
            instance = new Log4jLogger();

        return instance;
    }

    @Override
    public void debug(String message) {
        log.debug(message);
    }

    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void warn(String message) {
        log.warn(message);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }

    @Override
    public void fatal(String message, Throwable throwable) {
        log.fatal(message, throwable);
    }

    @Override
    public void fatal(String message) {
        log.fatal(message);
    }
}
