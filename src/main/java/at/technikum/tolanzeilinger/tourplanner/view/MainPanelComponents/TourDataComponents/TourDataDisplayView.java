package at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;

import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataCreateViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataDisplayViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TourDataDisplayView implements View {

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label fromLabel;

    @FXML
    private Label toLabel;

    @FXML
    private Label transportationLabel;

    @FXML
    private Label hillinessLabel;


    private final TourDataDisplayViewModel viewModel;

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


    }
}
