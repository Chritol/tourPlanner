package at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourMiscViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


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
