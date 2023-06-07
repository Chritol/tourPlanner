package at.technikum.tolanzeilinger.tourplanner.view.MainPanelComponents;

import at.technikum.tolanzeilinger.tourplanner.view.View;
import at.technikum.tolanzeilinger.tourplanner.viewModel.MainPanelComponents.TourMapViewModel;
import at.technikum.tolanzeilinger.tourplanner.viewModel.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class TourMapView implements View {

    @FXML
    private ImageView routeimage;

    private final TourMapViewModel viewModel;

    public TourMapView(TourMapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    public void initialize() {
        routeimage.setImage( ((TourMapViewModel) viewModel).getRouteImage() );
    }

}
