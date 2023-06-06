package at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class PDFcViewModel {

    public boolean isButtonDisabled() {
        return buttonDisabled.get();
    }

    public BooleanProperty buttonDisabledProperty() {
        return buttonDisabled;
    }

    public void setButtonDisabled(boolean buttonDisabled) {
        this.buttonDisabled.set(buttonDisabled);
    }

    private final BooleanProperty buttonDisabled = new SimpleBooleanProperty(false);
    public void createPDF() {
        System.out.println("PDF-Creator-Button pressed");
    }
}
