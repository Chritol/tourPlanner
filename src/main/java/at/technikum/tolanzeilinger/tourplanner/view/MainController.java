package at.technikum.tolanzeilinger.tourplanner.view;

import at.technikum.tolanzeilinger.tourplanner.viewModel.MainViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController {

    @FXML
    private ListView<String> names;

    @FXML
    private TextField input;

    private final MainViewModel viewModel;

    public MainController(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        names.setItems(viewModel.getNames());
        input.textProperty().bindBidirectional(viewModel.inputProperty());
        names.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> viewModel.setSelectedName(newValue));
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
