package at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.presentation.view.View;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourDataComponents.TourDataPaneSwitcherViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TourDataPaneSwitcherView implements View {
    @FXML
    public VBox display;
    @FXML
    public VBox create;
    @FXML
    public VBox edit;

    @FXML
    public Label youBrokeIt;


    private final TourDataPaneSwitcherViewModel viewModel;

    public TourDataPaneSwitcherView(TourDataPaneSwitcherViewModel viewModel) {
        this.viewModel = viewModel;
    }


    @Override
    public void initialize() {
        youBrokeIt.setVisible(false);

        display.visibleProperty().bindBidirectional(viewModel.displayVisibleProperty());
        create.visibleProperty().bindBidirectional(viewModel.createVisibleProperty());
        edit.visibleProperty().bindBidirectional(viewModel.editVisibleProperty());
    }
}
