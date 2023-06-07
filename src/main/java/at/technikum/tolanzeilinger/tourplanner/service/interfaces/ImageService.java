package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import javafx.scene.image.Image;

public interface ImageService {
    String saveImage(Image image);
    Image loadImage(String fileName);
}
