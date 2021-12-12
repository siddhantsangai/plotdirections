package com.locus.internal.interpolator;

import com.google.maps.model.LatLng;
import com.locus.external.apihandler.GoogleRouteRequestService;
import com.locus.plotdirections.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class QueryService {

    @Autowired
    private PropertyReader properties;

    @GetMapping("/query")
    public String plotRoute(@RequestParam(value="origin") String origin, @RequestParam(value="destination") String destination){
        StringBuilder sb=new StringBuilder();
        LatLonInterpolator interpolator = new LatLonInterpolator(properties.getRequiredMinDistance());
        GoogleRouteRequestService routeRequest=new GoogleRouteRequestService(origin,destination);
        List<LatLng> polyline = routeRequest.getRoute(properties.getApiKey());
        LatLng originLatLng=new LatLng(Double.parseDouble(origin.split(",")[0]),Double.parseDouble(origin.split(",")[1]));
        LatLng destinationLatLng=new LatLng(Double.parseDouble(destination.split(",")[0]),Double.parseDouble(destination.split(",")[1]));
        interpolator.getResultCoordinates().add(originLatLng);
        for(int i=0; i<polyline.size()-1; i++){
            interpolator.interpolateCoordinates(polyline.get(i).lat,polyline.get(i).lng,polyline.get(i+1).lat,polyline.get(i+1).lng);
        }
        interpolator.getResultCoordinates().add(destinationLatLng);
        interpolator.getResultCoordinates().forEach(element->
                sb.append(element.lat).append(",").append(element.lng).append("<br>"));
        return sb.toString();
    }
}
