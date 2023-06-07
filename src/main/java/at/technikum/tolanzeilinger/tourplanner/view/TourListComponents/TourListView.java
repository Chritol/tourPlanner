package at.technikum.tolanzeilinger.tourplanner.view.TourListComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents.TourListViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TourListView implements View {
    @FXML
    private ListView<String> tourNames;
    private final TourListViewModel viewModel;

    public TourListView(ViewModel viewModel) {
        this.viewModel = (TourListViewModel) viewModel;
    }

    @Override
    @FXML
    public void initialize() {
        tourNames.itemsProperty().bindBidirectional(viewModel.tourNamesProperty());
        tourNames.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> viewModel.selectTour(newValue));
    }
}
