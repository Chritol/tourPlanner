package at.technikum.tolanzeilinger.tourplanner.view.TourLogComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourListActionButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourLogsActionButtonsViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class TourLogsActionButtonsView  implements View {
    private final TourLogsActionButtonsViewModel viewModel;

    public TourLogsActionButtonsView(ViewModel viewModel) {
        this.viewModel = (TourLogsActionButtonsViewModel) viewModel;
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

    public void edit(MouseEvent mouseEvent) {
        viewModel.edit();
    }
}
