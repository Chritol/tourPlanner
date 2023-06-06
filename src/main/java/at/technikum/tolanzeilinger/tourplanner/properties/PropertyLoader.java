package at.technikum.tolanzeilinger.tourplanner.properties;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyLoader {
    private final EventAggregator eventAggregator;
    private final Logger logger;
    private final String propertyFileName;
    private final Map<String, String> propertiesMap;


    public PropertyLoader(String propertyFileName, Logger logger, EventAggregator eventAggregator) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;

        this.propertyFileName = propertyFileName;
        this.propertiesMap = new HashMap<>();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                propertiesMap.put(key, value);
            }
        } catch (IOException e) {
            logger.error( "Could not load properties file: " + propertyFileName, e );
            e.printStackTrace();
        }

        eventAggregator.publish(Event.PROPERTIES_LOADED);
    }

    public String getProperty(String key) {
        return propertiesMap.get(key);
    }

    public void setProperty(String key, String value) {
        propertiesMap.put(key, value);
    }
}
