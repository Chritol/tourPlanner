package at.technikum.tolanzeilinger.tourplanner.service.interfaces;

import at.technikum.tolanzeilinger.tourplanner.model.RouteItem;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;

public interface IRouteService {
    RouteItem loadRouteFromUrl(String url, Map<String, String> params) throws IOException, URISyntaxException, InterruptedException;

    Image getRouteImage(String url, String sessionId) throws URISyntaxException, IOException, InterruptedException;
}
