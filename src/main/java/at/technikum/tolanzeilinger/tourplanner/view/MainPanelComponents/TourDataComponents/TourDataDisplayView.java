package at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;

import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataCreateViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataDisplayViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TourDataDisplayView implements View {

    @FXML
    public Label nameLabel;

    @FXML
    public Label descriptionLabel;

    @FXML
    public Label fromLabel;

    @FXML
    public Label toLabel;

    @FXML
    public Label transportationLabel;

    @FXML
    public Label hillinessLabel;
    @FXML
    public Label distanceLabel;
    @FXML
    public Label estimatedTimeLabel;

    @FXML
    public ImageView mapImageView;

    @FXML
    public Label popularityLabel;

    @FXML
    public Label childFriendlinessLabel;

    private final TourDataDisplayViewModel viewModel;

    @FXML
    public Button submitButton;

    public TourDataDisplayView(TourDataDisplayViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize() {
        nameLabel.textProperty().bindBidirectional(viewModel.nameTextProperty());
        descriptionLabel.textProperty().bindBidirectional(viewModel.descriptionTextProperty());
        fromLabel.textProperty().bindBidirectional(viewModel.fromTextProperty());
        toLabel.textProperty().bindBidirectional(viewModel.toTextProperty());
        transportationLabel.textProperty().bindBidirectional(viewModel.transportationTextProperty());
        hillinessLabel.textProperty().bindBidirectional(viewModel.hillinessTextProperty());

        popularityLabel.textProperty().bind(viewModel.popularityTextProperty());
        childFriendlinessLabel.textProperty().bind(viewModel.childFriendlinessTextProperty());

        distanceLabel.textProperty().bindBidirectional(viewModel.distanceTextProperty());
        estimatedTimeLabel.textProperty().bindBidirectional(viewModel.estimatedTimeTextProperty());

        submitButton.disableProperty().bindBidirectional(viewModel.submitButtonIsActiveProperty());

        mapImageView.imageProperty().bind(viewModel.imagePropertyProperty());
    }

    @FXML
    private void handleEditButtonClicked() {
        viewModel.handleEditButtonClicked();
    }
}
