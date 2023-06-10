package at.technikum.tolanzeilinger.tourplanner.view.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents.PDFcViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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

    public void createPDFGlobe(MouseEvent mouseEvent) {
    }

    public void createExcelReport() { viewModel.createExcelReport(); }

    public void importExcelReport() { viewModel.importExcelReport(); }


}
