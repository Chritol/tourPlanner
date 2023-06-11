package at.technikum.tolanzeilinger.tourplanner.presentation.view.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents.PDFcViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PDFcView implements View {
    private final PDFcViewModel viewModel;

    @FXML
    private Button createPdfButton;

    @FXML
    private Button exportExcelReportButton;

    public PDFcView(PDFcViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        createPdfButton.disableProperty().bind(viewModel.pdfExportButtonDisabledProperty());
        exportExcelReportButton.disableProperty().bind(viewModel.excelExportButtonDisabledProperty());
    }

    public void createPDF() {
        viewModel.createPDF();
    }

    public void createPDFGlobe() {
        viewModel.createPDFGlobe();
    }

    public void createExcelReport() { viewModel.createExcelReport(); }

    public void importExcelReport() { viewModel.importExcelReport(); }
}
