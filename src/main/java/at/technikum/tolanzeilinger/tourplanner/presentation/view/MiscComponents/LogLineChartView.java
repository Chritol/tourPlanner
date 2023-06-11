package at.technikum.tolanzeilinger.tourplanner.presentation.view.MiscComponents;

import at.technikum.tolanzeilinger.tourplanner.presentation.view.View;
import at.technikum.tolanzeilinger.tourplanner.presentation.viewModel.MiscComponents.LogLineChartViewModel;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class LogLineChartView implements View {
    @FXML
    private LineChart<Number, Number> lineChart;

    private final LogLineChartViewModel logLineChartViewModel;

    public LogLineChartView(LogLineChartViewModel logLineChartViewModel) {
        this.logLineChartViewModel = logLineChartViewModel;
    }

    public void initialize() {
        logLineChartViewModel.totalTimeProperty().addListener((obs, oldValues, newValues) -> setData(newValues));
    }

    public void setData(List<Integer> data) {
        lineChart.getData().clear();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(data.isEmpty() ? "No data" : "Logs of tour: " + logLineChartViewModel.getActiveTourName());

        for (int i = 0; i < data.size(); i++) {
            series.getData().add(new XYChart.Data<>(i, data.get(i)));
        }

        lineChart.getData().add(series);
    }
}
