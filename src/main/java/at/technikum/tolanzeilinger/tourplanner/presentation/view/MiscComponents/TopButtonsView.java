package at.technikum.tolanzeilinger.tourplanner.presentation.view.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents.TopButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.View;
import javafx.fxml.FXML;

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
