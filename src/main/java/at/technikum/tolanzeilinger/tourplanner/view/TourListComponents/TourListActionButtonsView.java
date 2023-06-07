package at.technikum.tolanzeilinger.tourplanner.view.TourListComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourListActionButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
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

