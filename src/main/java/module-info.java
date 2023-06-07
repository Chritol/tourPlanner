module at.technikum.tolanzeilinger.tourplanner {
    exports at.technikum.tolanzeilinger.tourplanner.persistence;

    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.slf4j;
    requires org.apache.logging.log4j.core;
    requires org.hibernate.orm.core;
    requires org.postgresql.jdbc;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;

    opens at.technikum.tolanzeilinger.tourplanner to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner;
    exports at.technikum.tolanzeilinger.tourplanner.view;
    opens at.technikum.tolanzeilinger.tourplanner.view to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.viewModel;
    opens at.technikum.tolanzeilinger.tourplanner.viewModel to javafx.fxml;
    opens at.technikum.tolanzeilinger.tourplanner.service.implementations to java.net.http, com.fasterxml.jackson.databind;
    opens at.technikum.tolanzeilinger.tourplanner.model to com.fasterxml.jackson.databind;
    opens at.technikum.tolanzeilinger.tourplanner.persistence to org.hibernate.orm.core;
    opens at.technikum.tolanzeilinger.tourplanner.model.tours to com.fasterxml.jackson.databind;
    opens at.technikum.tolanzeilinger.tourplanner.persistence.repositories to com.fasterxml.jackson.databind;
    exports at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents;
    opens at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.view.MiscComponents;
    opens at.technikum.tolanzeilinger.tourplanner.view.MiscComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents;
    opens at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents;
    opens at.technikum.tolanzeilinger.tourplanner.viewModel.MiscComponents to javafx.fxml;
    opens at.technikum.tolanzeilinger.tourplanner.persistence.dao.models to org.hibernate.orm.core;
    exports at.technikum.tolanzeilinger.tourplanner.view.TourListComponents;
    opens at.technikum.tolanzeilinger.tourplanner.view.TourListComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents;
    opens at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourDataComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents;
    opens at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents.TourDataComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.view.TourLogComponents;
    opens at.technikum.tolanzeilinger.tourplanner.view.TourLogComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents;
    opens at.technikum.tolanzeilinger.tourplanner.viewModel.TourListComponents to javafx.fxml;
}