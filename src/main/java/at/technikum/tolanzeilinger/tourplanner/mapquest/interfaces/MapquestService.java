package at.technikum.tolanzeilinger.tourplanner.mapquest.interfaces;

import at.technikum.tolanzeilinger.tourplanner.mapquest.models.TourDtoModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public interface MapquestService {
    TourDtoModel fetchMapquestRoute(String url) throws IOException, URISyntaxException, InterruptedException;

    InputStream fetchMapquestImage(String url) throws URISyntaxException, IOException, InterruptedException;
}
