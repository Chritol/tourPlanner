package at.technikum.tolanzeilinger.tourplanner.service.api.interfaces;

import at.technikum.tolanzeilinger.tourplanner.service.api.models.TourDtoModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;

public interface MapquestService {
    TourDtoModel fetchMapquestRoute(String url) throws IOException, URISyntaxException, InterruptedException;

    InputStream fetchMapquestImage(String url) throws URISyntaxException, IOException, InterruptedException;
}
