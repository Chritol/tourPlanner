package at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.model.Hilltype;
import at.technikum.tolanzeilinger.tourplanner.model.Transportation;
import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataCreateViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TourDataCreateView implements View {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private ComboBox<Transportation> transportationComboBox;

    @FXML
    private ComboBox<Hilltype> hillinessComboBox;
    @FXML
    public Button submitButton;

    private final TourDataCreateViewModel viewModel;

    public TourDataCreateView(TourDataCreateViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        // Bind the UI controls to the ViewModel properties
        nameTextField.textProperty().bindBidirectional(viewModel.getNameProperty());
        descriptionTextField.textProperty().bindBidirectional(viewModel.getDescriptionProperty());
        fromTextField.textProperty().bindBidirectional(viewModel.getFromProperty());
        toTextField.textProperty().bindBidirectional(viewModel.getToProperty());
        transportationComboBox.valueProperty().bindBidirectional(viewModel.getTransportationProperty());
        hillinessComboBox.valueProperty().bindBidirectional(viewModel.getHillinessProperty());

        // Set the dropdown options from the ViewModel
        transportationComboBox.setItems(viewModel.getTransportationOptionsProperty().get());
        hillinessComboBox.setItems(viewModel.getHillinessOptionsProperty().get());

        nameTextField.borderProperty().bindBidirectional(viewModel.nameBorderPropertyProperty());
        descriptionTextField.borderProperty().bindBidirectional(viewModel.descriptionBorderPropertyProperty());
        fromTextField.borderProperty().bindBidirectional(viewModel.fromBorderPropertyProperty());
        toTextField.borderProperty().bindBidirectional(viewModel.toBorderPropertyProperty());
        transportationComboBox.borderProperty().bindBidirectional(viewModel.transportationBorderPropertyProperty());
        hillinessComboBox.borderProperty().bindBidirectional(viewModel.hillinessBorderPropertyProperty());

        submitButton.disableProperty().bindBidirectional(viewModel.submitButtonIsActiveProperty());
    }

    // Other methods and event handlers for the view can be added here

    @FXML
    public void handleSubmitButtonClicked(ActionEvent actionEvent) {
        viewModel.submit();
    }

    public void handleClose(MouseEvent mouseEvent) {
        viewModel.handleClose();
    }
}
