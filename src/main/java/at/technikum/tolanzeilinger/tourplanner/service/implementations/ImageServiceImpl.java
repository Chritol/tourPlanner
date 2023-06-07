package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.ImageService;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class ImageServiceImpl implements ImageService {
    private final Properties properties;
    private final Logger logger;
    private final EventAggregator eventAggregator;

    public ImageServiceImpl(Properties properties, EventAggregator eventAggregator, Logger logger) {
        this.properties = properties;
        this.eventAggregator = eventAggregator;
        this.logger = logger;
    }


    public String saveImage(Image image) {
        String imagePath = properties.getProperty("image.save.path");
        String imageName = generateImageName(image);
        Path targetPath = Path.of(imagePath, imageName);

        try {
            URL imageUrl = new URL(image.getUrl());
            InputStream inputStream = imageUrl.openStream();
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();

            // Publish the image saved event
            eventAggregator.publish(Event.IMAGE_SAVED);

            return imageName;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    public Image loadImage(String fileName) {
        String imagePath = properties.getProperty("image.load.path");
        String filePath = imagePath + fileName;

        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Image image = new Image(fileInputStream);
            fileInputStream.close();

            return image;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    private String generateImageName(Image image) {
        // Generate a hash of the image or use any other desired naming scheme
        // For simplicity, this example uses a basic hash code
        return Integer.toString(image.hashCode()) + ".jpg";
    }
}
