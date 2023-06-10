package at.technikum.tolanzeilinger.tourplanner.view.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents.SearchViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SearchView implements View {
    private final SearchViewModel viewModel;

    @FXML
    private TextField search;

    public SearchView(SearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    @FXML
    public void initialize() {
        // Add bindings here
    }

    @FXML
    private void handleSearch() {
        String searchString = search.getText();
        viewModel.search(searchString);
    }
}
