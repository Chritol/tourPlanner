package at.technikum.tolanzeilinger.tourplanner.view.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents.PDFcViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PDFcView implements View {
    private final PDFcViewModel viewModel;

    @FXML
    private Button createPdfButton;

    public PDFcView(PDFcViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        createPdfButton.disableProperty().bind(viewModel.buttonDisabledProperty());
    }

    public void createPDF() {
        viewModel.createPDF();
    }

}
