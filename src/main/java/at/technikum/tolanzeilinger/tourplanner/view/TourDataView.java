package at.technikum.tolanzeilinger.tourplanner.view;

import at.technikum.tolanzeilinger.tourplanner.viewModel.MainViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class TourDataView {

    @FXML
    private ListView<String> names;

    @FXML
    private TextField input;

    @FXML
    private ImageView routeimage;

    private final MainViewModel viewModel;

    public TourDataView(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        names.setItems(viewModel.getNames());
        input.textProperty().bindBidirectional(viewModel.inputProperty());
        names.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> viewModel.setSelectedName(newValue));
        routeimage.setImage(viewModel.getRouteImage());
    }

    @FXML
    public void enter() {
        viewModel.enterNewWord();
    }

    @FXML
    public void delete() {
        viewModel.deleteWord();
    }
}
