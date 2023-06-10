package at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents;

import at.technikum.tolanzeilinger.tourplanner.model.enums.HillType;
import at.technikum.tolanzeilinger.tourplanner.model.enums.Transportation;
import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents.TourDataEditViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TourDataEditView implements View {

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
    private ComboBox<HillType> hillinessComboBox;

    private final TourDataEditViewModel viewModel;

    @FXML
    public Button submitButton;

    public TourDataEditView(TourDataEditViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        // Bind the UI controls to the ViewModel properties
        nameTextField.textProperty().bindBidirectional(viewModel.namePropertyProperty());
        descriptionTextField.textProperty().bindBidirectional(viewModel.descriptionPropertyProperty());
        fromTextField.textProperty().bindBidirectional(viewModel.fromPropertyProperty());
        toTextField.textProperty().bindBidirectional(viewModel.toPropertyProperty());
        transportationComboBox.valueProperty().bindBidirectional(viewModel.transportationPropertyProperty());
        hillinessComboBox.valueProperty().bindBidirectional(viewModel.hillinessPropertyProperty());

        // Set the dropdown options from the ViewModel
        transportationComboBox.setItems(viewModel.transportationOptionsPropertyProperty().get());
        hillinessComboBox.setItems(viewModel.hillinessOptionsPropertyProperty().get());

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
