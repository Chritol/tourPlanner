package at.technikum.tolanzeilinger.tourplanner.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class Log4jLogger implements Logger {
    private final org.apache.logging.log4j.Logger log;

    public Log4jLogger() {
        ConfigurationBuilder<BuiltConfiguration> builder
                = ConfigurationBuilderFactory.newConfigurationBuilder();

        LayoutComponentBuilder standard
                = builder.newLayout("PatternLayout");
        standard.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");

        AppenderComponentBuilder console
                = builder.newAppender("stdout", "Console");

        console.add(standard);
        builder.add(console);

        AppenderComponentBuilder file = builder
                .newAppender("LogToFile", "File")
                .addAttribute("fileName", "target/logging.log");

        file.add(standard);
        builder.add(file);

        RootLoggerComponentBuilder rootLogger = builder
                .newRootLogger(Level.ALL)
                .add(builder.newAppenderRef("stdout"));

        rootLogger.add(builder.newAppenderRef("LogToFile"));
        builder.add(rootLogger);

        LoggerContext context = Configurator.initialize(builder.build());
        context.start();
        log = context.getLogger("log");
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
}
