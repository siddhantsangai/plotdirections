package com.locus.external.apihandler;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoogleRouteRequestService implements RouteRequestService{

    private String origin;
    private String destination;

    public GoogleRouteRequestService(String origin, String destination){
        this.origin=origin;
        this.destination=destination;
    }

    @Override
    public List<LatLng> getRoute(String apiKey){
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        DirectionsApiRequest req = DirectionsApi.getDirections(context, this.origin, this.destination);
        DirectionsResult result= null;

        try {
            result = req.await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        if(result.routes.length>0){
            List<LatLng> polyCoordinates=new ArrayList<>();
            DirectionsRoute route=result.routes[0];
            DirectionsLeg[] legs=route.legs;
            Arrays.stream(legs).forEach(leg -> {
                DirectionsStep[] steps=leg.steps;
                Arrays.stream(steps).forEach(step -> {
                    polyCoordinates.addAll(step.polyline.decodePath());
                });
            });
            return polyCoordinates;
        }
        return new ArrayList<>();
    }
}
