package at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourMiscViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class TourMiscView implements View {

    @FXML
    public Label nameLabel;

    private final TourMiscViewModel viewModel;

    public TourMiscView(TourMiscViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        nameLabel.textProperty().bindBidirectional(viewModel.nameTextProperty());
    }

}
