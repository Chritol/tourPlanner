package at.technikum.tolanzeilinger.tourplanner.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class JsonObject {
    private RouteItem route;

    public RouteItem getRoute() {
        return route;
    }

    public void setRoute(RouteItem route) {
        this.route = route;
    }
}
