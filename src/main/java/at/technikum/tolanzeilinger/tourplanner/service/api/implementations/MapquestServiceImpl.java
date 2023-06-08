package at.technikum.tolanzeilinger.tourplanner.service.api.implementations;

import at.technikum.tolanzeilinger.tourplanner.service.api.interfaces.MapquestUrlBuilderService;
import at.technikum.tolanzeilinger.tourplanner.service.api.models.RootJsonObjectDtoModel;
import at.technikum.tolanzeilinger.tourplanner.service.api.models.TourDtoModel;
import at.technikum.tolanzeilinger.tourplanner.service.api.interfaces.MapquestService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

// just for talking to the api
public class MapquestServiceImpl implements MapquestService {
    private final MapquestUrlBuilderService mapquestUrlBuilderService;

    public MapquestServiceImpl(MapquestUrlBuilderService mapquestUrlBuilderService) {
        this.mapquestUrlBuilderService = mapquestUrlBuilderService;
    }

    @Override
    public TourDtoModel fetchMapquestRoute(String url) throws IOException, URISyntaxException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() == 402) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TourDtoModel tourDtoModel = mapper.readValue(response.body(), RootJsonObjectDtoModel.class).getRoute();

        return tourDtoModel;
    }

    @Override
    public InputStream fetchMapquestImage(String url) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if(response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }
}
