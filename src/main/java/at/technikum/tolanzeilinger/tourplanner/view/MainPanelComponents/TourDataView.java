package at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.model.tours.Hilltype;
import at.technikum.tolanzeilinger.tourplanner.model.tours.Transportation;
import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class TourDataView implements View {

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

    private final TourDataViewModel viewModel;

    public TourDataView(TourDataViewModel viewModel) {
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
        transportationComboBox.setItems(viewModel.getTransportationOptionsProperty());
        hillinessComboBox.setItems((ObservableList<Hilltype>) viewModel.getHillinessOptionsProperty());

    }

    // Other methods and event handlers for the view can be added here

    @FXML
    public void handleSubmitButtonClicked(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String description = descriptionTextField.getText();
        String from = fromTextField.getText();
        String to = toTextField.getText();
        Transportation transportation = transportationComboBox.getValue();
        Hilltype hilliness = hillinessComboBox.getValue();

        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Transportation: " + transportation);
        System.out.println("Hilliness: " + hilliness);
    }

}
