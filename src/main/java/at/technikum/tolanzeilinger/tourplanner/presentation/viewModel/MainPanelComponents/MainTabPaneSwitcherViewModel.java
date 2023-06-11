package at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.MainTabPaneSwitcherView;
import at.technikum.tolanzeilinger.tourplanner.presentation.view.View;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;

public class MainTabPaneSwitcherViewModel implements ViewModel {
    private final EventAggregator eventAggregator;
    private final Logger logger;

    private final IntegerProperty selectedTabIndex = new SimpleIntegerProperty(0);

    public MainTabPaneSwitcherViewModel(
            EventAggregator eventAggregator,
            Logger logger
    ) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;

        initializeView();
        initializeEventListeners();
    }

    @Override
    public void initializeView() {    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.EXIT_FORM_TOUR_ACTION, this::switchToGeneral);
        eventAggregator.addSubscriber(Event.EDIT_TOUR_ACTION, this::switchToGeneral);
        eventAggregator.addSubscriber(Event.NEW_TOUR_ACTION, this::switchToGeneral);

        eventAggregator.addSubscriber(Event.OPEN_MAP_ACTION, this::switchToMap);

        eventAggregator.addSubscriber(Event.OPEN_MISC_ACTION, this::switchToMisc);
    }

    private void switchToMisc() {
        logger.info("Switching to misc tab");

        view.changeTab(2);
    }

    private void switchToMap() {
        logger.info("Switching to map tab");

        view.changeTab(1);
    }

    private void switchToGeneral() {
        logger.info("Switching to general tab");

        view.changeTab(0);
    }

    private MainTabPaneSwitcherView view;
    public void tightlyLock(View view) {
        // I know that we shouldn't be doing this, but there is literally no other way because the tabSelectionProperty is ReadOnly.
        this.view = (MainTabPaneSwitcherView) view;
    }
}
