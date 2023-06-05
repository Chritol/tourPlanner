package at.technikum.tolanzeilinger.tourplanner.view;

import at.technikum.tolanzeilinger.tourplanner.viewModel.PDFcViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PDFcView extends View{
    private final PDFcViewModel viewModel;

    @FXML
    private Button createPdfButton;

    public PDFcView(PDFcViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    void initialize() {
        createPdfButton.disableProperty().bind(viewModel.buttonDisabledProperty());
    }

    public void createPDF() {
        viewModel.createPDF();
    }

}
