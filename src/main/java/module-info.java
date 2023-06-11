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
    requires org.apache.pdfbox;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens at.technikum.tolanzeilinger.tourplanner to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.view;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.view to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.viewModel;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.viewModel to javafx.fxml;
    opens at.technikum.tolanzeilinger.tourplanner.service.implementations to java.net.http, com.fasterxml.jackson.databind;
    opens at.technikum.tolanzeilinger.tourplanner.model to com.fasterxml.jackson.databind;
    opens at.technikum.tolanzeilinger.tourplanner.persistence to org.hibernate.orm.core, com.fasterxml.jackson.databind;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.view.MiscComponents;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.view.MiscComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents to javafx.fxml;
    opens at.technikum.tolanzeilinger.tourplanner.persistence.dao.models to org.hibernate.orm.core;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.view.TourListComponents;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.view.TourListComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourDataComponents;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MainPanelComponents.TourDataComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.TourDataComponents;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.view.MainPanelComponents.TourDataComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.view.TourLogComponents;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.view.TourLogComponents to javafx.fxml;
    exports at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.TourListComponents;
    opens at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.TourListComponents to javafx.fxml;
    opens at.technikum.tolanzeilinger.tourplanner.mapquest.interfaces to com.fasterxml.jackson.databind, java.net.http;
    opens at.technikum.tolanzeilinger.tourplanner.mapquest.implementations to com.fasterxml.jackson.databind, java.net.http;
    opens at.technikum.tolanzeilinger.tourplanner.mapquest.models to com.fasterxml.jackson.databind, java.net.http;
    opens at.technikum.tolanzeilinger.tourplanner.model.enums to com.fasterxml.jackson.databind;
    opens at.technikum.tolanzeilinger.tourplanner.mapquest.models.enums to com.fasterxml.jackson.databind, java.net.http;
}