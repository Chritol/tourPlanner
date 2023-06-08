package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import javafx.scene.image.Image;

import java.io.InputStream;

public interface ImageStorageService {
    boolean saveImage(InputStream imageInput, long tourId);
    Image loadImage(long tourId);
}
