package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public interface FolderOpenerService {

    void openFileDirectory();
    void openPicturesDirectory();

    void createDirectoryIfNotExists(String directoryPath) ;
}
