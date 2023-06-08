package at.technikum.tolanzeilinger.tourplanner.view;

import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.fxml.FXML;

public class DummyView  implements View {
    private final ViewModel viewModel;

    public DummyView(ViewModel viewModel) {
        this.viewModel = (ViewModel) viewModel;
    }

    @Override
    @FXML
    public void initialize() {
        //Add bindings here
    }
}
