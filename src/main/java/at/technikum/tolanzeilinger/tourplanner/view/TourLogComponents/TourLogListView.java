package at.technikum.tolanzeilinger.tourplanner.view.TourLogComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.TourLogComponents.TourLogListViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDateTime;

public class TourLogListView implements View {
    private final TourLogListViewModel viewModel;

    @FXML
    private TableView<TourLogListViewModel.TourLogItem> logTable;

    @FXML
    private TableColumn<TourLogListViewModel.TourLogItem, LocalDateTime> dateTimeColumn;

    @FXML
    private TableColumn<TourLogListViewModel.TourLogItem, String> commentColumn;

    @FXML
    private TableColumn<TourLogListViewModel.TourLogItem, String> difficultyColumn;

    @FXML
    private TableColumn<TourLogListViewModel.TourLogItem, String> totalTimeColumn;

    @FXML
    private TableColumn<TourLogListViewModel.TourLogItem, String> ratingColumn;

    public TourLogListView(ViewModel viewModel) {
        this.viewModel = (TourLogListViewModel) viewModel;
    }

    @Override
    @FXML
    public void initialize() {
        logTable.setItems(viewModel.getTourLogs());
        dateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        commentColumn.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
        difficultyColumn.setCellValueFactory(cellData -> cellData.getValue().difficultyProperty());
        totalTimeColumn.setCellValueFactory(cellData -> cellData.getValue().totalTimeProperty());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());

        // Add event listener for table item click
        logTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                viewModel.handleTableItemClick(logTable.getSelectionModel().getSelectedItem());
            }
        });
    }
}
