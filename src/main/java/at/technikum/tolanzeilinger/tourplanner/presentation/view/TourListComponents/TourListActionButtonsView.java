package at.technikum.tolanzeilinger.tourplanner.presentation.view.TourListComponents;

import at.technikum.tolanzeilinger.tourplanner.presentation.view.View;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.TourListComponents.TourListActionButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class TourListActionButtonsView implements View {
    private final TourListActionButtonsViewModel viewModel;

    public TourListActionButtonsView(ViewModel viewModel) {
        this.viewModel = (TourListActionButtonsViewModel) viewModel;
    }

    @Override
    @FXML
    public void initialize() {}


    public void add(MouseEvent mouseEvent) {
        viewModel.add();
    }

    public void remove(MouseEvent mouseEvent) {
        viewModel.remove();
    }

    public void manual(MouseEvent mouseEvent) {
        viewModel.manual();
    }
}

