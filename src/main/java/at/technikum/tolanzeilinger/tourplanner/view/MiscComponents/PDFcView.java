package at.technikum.tolanzeilinger.tourplanner.view.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents.PDFcViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PDFcView implements View {
    private final PDFcViewModel viewModel;

    @FXML
    private Button createPdfButton;

    @FXML
    private Button createExcelReportButton;

    public PDFcView(PDFcViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        createPdfButton.disableProperty().bind(viewModel.pdfExportButtonDisabledProperty());
        createExcelReportButton.disableProperty().bind(viewModel.excelExportButtonDisabledProperty());
    }

    public void createPDF() {
        viewModel.createPDF();
    }

    public void createExcelReport() { viewModel.createExcelReport(); }

}
