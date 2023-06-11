package at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourMapViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TourMapView implements View {

    @FXML
    public Label nameLabel;
    @FXML
    public ImageView mapImageView;

    private final TourMapViewModel viewModel;

    public TourMapView(TourMapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        nameLabel.textProperty().bindBidirectional(viewModel.nameTextProperty());

        mapImageView.imageProperty().bind(viewModel.imagePropertyProperty());    }

}
