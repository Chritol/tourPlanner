module at.technikum.tolanzeilinger.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens at.technikum.tolanzeilinger.tourplanner to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner;
    exports at.technikum.tolanzeilinger.tourplanner.view;
    opens at.technikum.tolanzeilinger.tourplanner.view to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.viewModel;
    opens at.technikum.tolanzeilinger.tourplanner.viewModel to javafx.fxml;
    opens at.technikum.tolanzeilinger.tourplanner.service.implementations to java.net.http, com.fasterxml.jackson.databind;
    opens at.technikum.tolanzeilinger.tourplanner.model to com.fasterxml.jackson.databind;
}