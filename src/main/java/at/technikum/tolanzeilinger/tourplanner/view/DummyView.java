package at.technikum.tolanzeilinger.tourplanner.view;

import at.technikum.tolanzeilinger.tourplanner.viewModel.DummyViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.fxml.FXML;

public class DummyView  implements View {
    private final DummyViewModel viewModel;

    public DummyView(ViewModel viewModel) {
        this.viewModel = (DummyViewModel) viewModel;
    }

    @Override
    @FXML
    public void initialize() {
        //Add bindings here
    }
}
