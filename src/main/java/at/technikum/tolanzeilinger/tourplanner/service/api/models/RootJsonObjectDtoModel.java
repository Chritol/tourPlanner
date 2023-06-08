package at.technikum.tolanzeilinger.tourplanner.service.api.models;

public class RootJsonObjectDtoModel {
    private TourDtoModel route;

    public TourDtoModel getRoute() {
        return route;
    }

    public void setRoute(TourDtoModel route) {
        this.route = route;
    }
}
