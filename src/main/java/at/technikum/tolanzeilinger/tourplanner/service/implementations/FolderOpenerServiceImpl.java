package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.constants.PropertyConstants;
import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.PropertyLoaderService;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.FolderOpenerService;

import java.awt.Desktop;

import java.io.File;
import java.io.IOException;

public class FolderOpenerServiceImpl implements FolderOpenerService {

    private final EventAggregator eventAggregator;
    private final Logger logger;
    private final PropertyLoaderService propertyLoaderService;

    public FolderOpenerServiceImpl(PropertyLoaderService propertyLoaderService, Logger logger, EventAggregator eventAggregator) {
        this.logger = logger;
        this.eventAggregator = eventAggregator;

        this.propertyLoaderService = propertyLoaderService;

        eventAggregator.addSubscriber(Event.OPEN_FILE_DIRECTORY_ACTION, this::openFileDirectory);
        eventAggregator.addSubscriber(Event.PDF_CREATED, this::openFileDirectory);
        eventAggregator.addSubscriber(Event.EXCEL_CREATED, this::openExcelDirectory);
        eventAggregator.addSubscriber(Event.OPEN_PICTURES_DIRECTORY_ACTION, this::openPicturesDirectory);
    }

    @Override
    public void openExcelDirectory() {
        String directoryPath = propertyLoaderService.getProperty(PropertyConstants.REPORT_SAVE_PATH);
        tryOpenDirectory(directoryPath);
    }

    @Override
    public void openFileDirectory() {
        String directoryPath = propertyLoaderService.getProperty(PropertyConstants.PDF_SAVE_PATH);
        tryOpenDirectory(directoryPath);
    }
    @Override
    public void openPicturesDirectory() {
        String directoryPath = propertyLoaderService.getProperty(PropertyConstants.IMAGE_SAVE_PATH);
        tryOpenDirectory(directoryPath);
    }

    @Override
    public void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                logger.info("Directory created: " + directory.getAbsolutePath());
            } else {
                logger.error("Failed to create directory: " + directory.getAbsolutePath());
            }
        }
    }

    private void tryOpenDirectory(String directoryPath) {
        createDirectoryIfNotExists(directoryPath);

        logger.info("Opening file directory: " + directoryPath);

        try {
            Desktop.getDesktop().open(new File(directoryPath));
        } catch (IOException e) {
            logger.error("Failed to open file directory: " + directoryPath, e);
        }
    }
}
