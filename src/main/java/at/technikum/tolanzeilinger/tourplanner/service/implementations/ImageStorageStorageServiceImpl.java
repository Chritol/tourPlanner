package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.ImageStorageService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.PropertyLoaderService;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageStorageStorageServiceImpl implements ImageStorageService {
    private final Logger logger;
    private final EventAggregator eventAggregator;

    private final String rootPath;

    public ImageStorageStorageServiceImpl(PropertyLoaderService propertyLoaderService, EventAggregator eventAggregator, Logger logger) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;

        rootPath = propertyLoaderService.getProperty("image.save.path");
        createDirectoryIfNotExists(rootPath);
    }


    public boolean saveImage(InputStream imageInput, long tourId) {
        String imagePath = rootPath + "/" + tourId + ".png";

        try (imageInput) {
            Path outputFilePath = Path.of(imagePath);
            Files.copy(imageInput, outputFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Publish the image saved event
            eventAggregator.publish(Event.IMAGE_SAVED);

            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return false;
        }
    }

    public Image loadImage(long tourId) {
        String imagePath = rootPath + "/" + tourId + ".png";

        try {
            try (InputStream inputStream = new FileInputStream(imagePath)) {
                return new Image(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info("Directory created: " + directory.getAbsolutePath());
            } else {
                logger.error("Failed to create directory: " + directory.getAbsolutePath());
            }
        }
    }
}
