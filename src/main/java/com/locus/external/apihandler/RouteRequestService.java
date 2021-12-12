package com.locus.external.apihandler;

import com.google.maps.model.LatLng;

import java.util.List;

public interface RouteRequestService {
    public List<LatLng> getRoute(String apiKey);
}
