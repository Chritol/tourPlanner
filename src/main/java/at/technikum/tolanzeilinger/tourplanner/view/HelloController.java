package at.technikum.tolanzeilinger.tourplanner.view;

import at.technikum.tolanzeilinger.tourplanner.viewModel.HelloViewModel;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label counter;

    @FXML
    private VBox box;

    @FXML
    private TextField first;

    @FXML
    private TextField second;

    @FXML
    private Label sum;

    private int count;

    private final HelloViewModel viewModel = new HelloViewModel();

    public HelloController(){
        count = 0;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onCounterClick() {
        count ++;
        counter.setText(String.valueOf(count));
        box.setBackground(new Background(new BackgroundFill(Color.color(Math.random(), Math.random(), Math.random()), new CornerRadii(0), new Insets(0))));
    }

    @FXML
    protected void onAddClick() {
        try {
            double first = Double.parseDouble(this.first.getText());
            double second = Double.parseDouble(this.second.getText());
            sum.setText(String.valueOf(first + second));
        } catch (NumberFormatException e) {
            sum.setText("Not valid numbers");
        }

    }
}