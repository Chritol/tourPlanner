package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.constants.PropertyConstants;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.helpers.FileNameGenerator;
import at.technikum.tolanzeilinger.tourplanner.helpers.FileType;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.FolderOpenerService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.ImageStorageService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.PropertyLoaderService;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageStorageStorageServiceImpl implements ImageStorageService {
    private final Logger logger;
    private final EventAggregator eventAggregator;
    private final FolderOpenerService folderOpenerService;
    private final PropertyLoaderService propertyLoaderService;

    public ImageStorageStorageServiceImpl(PropertyLoaderService propertyLoaderService, EventAggregator eventAggregator, Logger logger, FolderOpenerService folderOpenerService) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;
        this.folderOpenerService = folderOpenerService;
        this.propertyLoaderService = propertyLoaderService;
    }


    public boolean saveImage(InputStream imageInput, long tourId) {
        String rootPath = propertyLoaderService.getProperty(PropertyConstants.IMAGE_SAVE_PATH);
        folderOpenerService.createDirectoryIfNotExists(rootPath);
        String imagePath = FileNameGenerator.generateFileName(rootPath, String.valueOf(tourId), FileType.PNG, false);

        try (imageInput) {
            Path outputFilePath = Path.of(imagePath);
            Files.copy(imageInput, outputFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Publish the image saved event
            eventAggregator.publish(Event.IMAGE_SAVED);
            logger.info("Stored new image. Path: " + imagePath);

            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return false;
        }
    }

    public Image loadImage(long tourId) {
        String rootPath = propertyLoaderService.getProperty(PropertyConstants.IMAGE_SAVE_PATH);
        String imagePath = FileNameGenerator.generateFileName(rootPath, String.valueOf(tourId), FileType.PNG, false);

        try {
            try (InputStream inputStream = new FileInputStream(imagePath)) {
                logger.info("Trying to load image with path: " + imagePath);
                return new Image(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }
}
