package org.example.screen;

import com.example.routing.Route;

public class BasicRoutingData {
    private Route route;
    private Long addictionDetailsId;

    public BasicRoutingData(){}

    public BasicRoutingData(Route route){
        this.route = route;
    }

    public BasicRoutingData(Route route, Long id){
        this.route = route;
        this.addictionDetailsId = id;
    }


    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Long getAddictionDetailsId() {
        return addictionDetailsId;
    }

    public void setAddictionDetailsId(Long addictionDetailsId) {
        this.addictionDetailsId = addictionDetailsId;
    }

    public boolean shouldExitApp(){
        return route == Route.EXIT;
    }
}
