package at.technikum.tolanzeilinger.tourplanner.viewModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HelloViewModel {
    private StringProperty firstNumber;
    private StringProperty secondNumber;
    private StringProperty result;

    public HelloViewModel() {
        this.firstNumber = new SimpleStringProperty("");
        this.secondNumber = new SimpleStringProperty("");
        this.result = new SimpleStringProperty("");
    }

    public String getFirstNumber() {
        return firstNumber.get();
    }

    public StringProperty firstNumberProperty() {
        return firstNumber;
    }

    public void setFirstNumber(String firstNumber) {
        this.firstNumber.set(firstNumber);
    }

    public String getSecondNumber() {
        return secondNumber.get();
    }

    public StringProperty secondNumberProperty() {
        return secondNumber;
    }

    public void setSecondNumber(String secondNumber) {
        this.secondNumber.set(secondNumber);
    }

    public String getResult() {
        return result.get();
    }

    public StringProperty resultProperty() {
        return result;
    }

    public void setResult(String result) {
        this.result.set(result);
    }

    public void sum() {
        try {
            double firstNumberAsDouble = Double.parseDouble(firstNumber.get());
            double secondNumberAsDouble = Double.parseDouble(secondNumber.get());
        } catch (NullPointerException npex) {
            result.set("Invalid input");
        } catch (NumberFormatException nfex) {

        }

        result.set(firstNumber.get() + secondNumber.get());
    }
}
