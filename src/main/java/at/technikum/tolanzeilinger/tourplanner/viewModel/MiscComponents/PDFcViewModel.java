package at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.event.Event;
import at.technikum.tolanzeilinger.tourplanner.event.EventAggregator;
import at.technikum.tolanzeilinger.tourplanner.log.Logger;
import at.technikum.tolanzeilinger.tourplanner.model.TourLog;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.TourService;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class PDFcViewModel implements ViewModel {

    private final TourService tourService;
    BooleanProperty buttonDisabled = new SimpleBooleanProperty(false);

    private final EventAggregator eventAggregator;
    private final Logger logger;

    public PDFcViewModel(
            EventAggregator eventAggregator,
            Logger logger,
            TourService tourService
    ) {
        this.eventAggregator = eventAggregator;
        this.logger = logger;
        this.tourService = tourService;

        initializeView();
        initializeEventListeners();
    }

    public boolean isButtonDisabled() {
        return buttonDisabled.get();
    }

    public BooleanProperty buttonDisabledProperty() {
        return buttonDisabled;
    }

    public void setButtonDisabled(boolean buttonDisabled) {
        this.buttonDisabled.set(buttonDisabled);
    }


    @Override
    public void initializeView() {
        updatePDFbutton();
    }

    @Override
    public void initializeEventListeners() {
        eventAggregator.addSubscriber(Event.TOUR_CHANGED, this::updatePDFbutton);
    }

    private void updatePDFbutton() {
        logger.info("updating PDF-Button");

        if (tourService.getActiveTourIndex() < 0) {
            buttonDisabled.set(true);
        } else {
            buttonDisabled.set(false);
        }
    }

    public void createPDF() {
        logger.info("PDF-Creator-Button pressed");

        eventAggregator.publish(Event.PDF_CREATE_ACTION);
    }
}
