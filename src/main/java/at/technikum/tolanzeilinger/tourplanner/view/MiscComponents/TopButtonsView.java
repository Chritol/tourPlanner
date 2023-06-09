package at.technikum.tolanzeilinger.tourplanner.view.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents.TopButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TopButtonsView implements View {
    private final TopButtonsViewModel viewModel;

    public TopButtonsView(TopButtonsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    @FXML
    public void initialize() {
        // Add bindings here
    }

    @FXML
    private void handleFileButtonClick() {
        viewModel.handleFileButtonClick();
    }

    @FXML
    private void handleEditButtonClick() {
        viewModel.handleEditButtonClick();
    }

    @FXML
    private void handleOptionsButtonClick() {
        viewModel.handleOptionsButtonClick();
    }

    @FXML
    private void handleMapButtonClick() {
        viewModel.handleMapButtonClick();
    }

    @FXML
    private void handlePicturesButtonClick() {
        viewModel.handlePicturesButtonClick();
    }

    @FXML
    private void handleHelpButtonClick() {
        viewModel.handleHelpButtonClick();
    }
}
