package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.Tour;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;

import java.util.List;

public interface PdfService {
    void generatePDFWithImageAndData();

    void createAndSavePDFOverview();
}
