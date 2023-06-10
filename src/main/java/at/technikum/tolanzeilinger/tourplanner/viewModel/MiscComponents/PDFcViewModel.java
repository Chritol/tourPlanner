package at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.helpers.FileType;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.*;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.File;

public class PDFcViewModel implements ViewModel {

    private final TourService tourService;
    BooleanProperty pdfExportButtonDisabled = new SimpleBooleanProperty(false);
    BooleanProperty excelExportButtonDisabled = new SimpleBooleanProperty(false);

    private final EventAggregator eventAggregator;
    private final Logger logger;

    private final ExportDataService exportDataService;
    private final PdfService pdfService;
    private final ImportDataService importDataService;
    private final FilePickerService filePickerService;

    public PDFcViewModel(
            EventAggregator eventAggregator,
            Logger logger,
            TourService tourService,
            ExportDataService exportDataService,
            PdfService pdfService,
            ImportDataService importDataService,
            FilePickerService filePickerService
    ) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;
        this.tourService = tourService;
        this.exportDataService = exportDataService;
        this.pdfService = pdfService;
        this.importDataService = importDataService;
        this.filePickerService = filePickerService;

        initializeView();
        initializeEventListeners();
    }

    public BooleanProperty pdfExportButtonDisabledProperty() {
        return pdfExportButtonDisabled;
    }

    public BooleanProperty excelExportButtonDisabledProperty() {
        return excelExportButtonDisabled;
    }

    @Override
    public void initializeView() {
        updatePdfButton();
        updateExcelButton();
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.TOUR_CHANGED, this::updatePdfButton);
        eventAggregator.addSubscriber(Event.TOUR_CHANGED, this::updateExcelButton);
    }

    private void updatePdfButton() {
        logger.info("updating PDF-export-Button");
        pdfExportButtonDisabled.set(tourService.getActiveTourIndex() < 0);
    }

    private void updateExcelButton() {
        logger.info("updating Excel-export-Button");
        excelExportButtonDisabled.set(tourService.getTours().size() < 1);
    }

    public void createPDF() {
        logger.info("PDF-Creator-Button pressed");
        pdfService.generatePDFWithImageAndData();
    }

    public void createExcelReport() {
        logger.info("Excel-Report-Creator-Button pressed");
        exportDataService.exportToursAndLogs();
    }

    public void importExcelReport() {
        File file = filePickerService.pickFile(FileType.EXCEL);
        if (file != null) {
            logger.info("Trying to import report");
            importDataService.importExcel(file.getAbsolutePath());
        } else {
            logger.error("Could not import data. Invalid file");
        }
    }

    public void createPDFGlobe() {
        logger.info("PDF-Creator-Button pressed");
        pdfService.createAndSavePDFOverview();
    }
}
