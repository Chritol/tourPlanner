package at.technikum.tolanzeilinger.tourplanner.service.implementations;

import at.technikum.tolanzeilinger.tourplanner.model.JsonObject;
import at.technikum.tolanzeilinger.tourplanner.model.RouteItem;
import at.technikum.tolanzeilinger.tourplanner.model.repositories.TourRepository;
import at.technikum.tolanzeilinger.tourplanner.model.repositories.WordRepository;
import at.technikum.tolanzeilinger.tourplanner.service.interfaces.IRouteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

// just for talking to the api
public class RouteService implements IRouteService {


    @Override
    public RouteItem loadRouteFromUrl(String url, Map<String, String> params) throws IOException, URISyntaxException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RouteItem routeItem = mapper.readValue(response.body(), JsonObject.class).getRoute();

        return routeItem;
    }

    @Override
    public Image getRouteImage(String url, String sessionId) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url + sessionId))
                .GET()
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        return new Image(response.body());
    }
}
