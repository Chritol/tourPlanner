package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

public interface PdfService {
    void generatePDFWithImageAndData();

    void createAndSavePDFOverview();
}
