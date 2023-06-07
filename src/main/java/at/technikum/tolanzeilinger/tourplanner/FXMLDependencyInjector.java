package at.technikum.tolanzeilinger.tourplanner;

import at.technikum.tolanzeilinger.tourplanner.factory.ViewFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLDependencyInjector {
    public static Parent load(String location, Locale locale) throws IOException {
        FXMLLoader loader = getLoader(location, locale);
        return loader.load();
    }

    public static FXMLLoader getLoader(String location, Locale locale) {
        return new FXMLLoader(
                FXMLDependencyInjector.class.getResource("/at/technikum/tolanzeilinger/tourplanner/" + location),
                ResourceBundle.getBundle("at.technikum.tolanzeilinger.tourplanner.gui", locale),
                new JavaFXBuilderFactory(),
                viewClass -> ViewFactory.getInstance().createView(viewClass)
        );
    }
}
