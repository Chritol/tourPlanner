package at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.MainTabPaneSwitcherViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainTabPaneSwitcherView implements View {

    @FXML
    public TabPane tabPane;

    private final MainTabPaneSwitcherViewModel viewModel;

    public MainTabPaneSwitcherView(MainTabPaneSwitcherViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void initialize() {
        //bind selected tab to selectedTabIndex in view model
        viewModel.tightlyLock(this);
    }

    public void changeTab(int index) {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(index);
    }
}
