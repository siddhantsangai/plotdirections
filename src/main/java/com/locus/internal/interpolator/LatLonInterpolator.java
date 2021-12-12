package com.locus.internal.interpolator;

import com.google.maps.model.LatLng;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import net.sf.geographiclib.GeodesicLine;
import net.sf.geographiclib.GeodesicMask;

import java.util.ArrayList;
import java.util.List;

public class LatLonInterpolator {

    private double requiredMinDistance;
    private double offset;
    private List<LatLng> resultCoordinates;
    public LatLonInterpolator(double requiredMinDistance){
        this.requiredMinDistance=requiredMinDistance;
        this.offset=requiredMinDistance;
        resultCoordinates=new ArrayList<>();
    }

    public List<LatLng> getResultCoordinates() {
        return resultCoordinates;
    }

    public void interpolateCoordinates(double lat1, double lon1, double lat2, double lon2){
        Geodesic geod = Geodesic.WGS84;
        GeodesicLine line = geod.InverseLine(lat1, lon1, lat2, lon2,
                GeodesicMask.DISTANCE_IN | GeodesicMask.LATITUDE | GeodesicMask.LONGITUDE);

        LatLng lastCoordinate = new LatLng();
        lastCoordinate.lat=lat1;
        lastCoordinate.lng=lon1;

        if(line.Distance()>offset)
        {
            GeodesicData g=line.Position(offset, GeodesicMask.LATITUDE | GeodesicMask.LONGITUDE);
            line=geod.InverseLine(g.lat2, g.lon2, lat2, lon2,
                    GeodesicMask.DISTANCE_IN | GeodesicMask.LATITUDE | GeodesicMask.LONGITUDE);
            lastCoordinate.lat=g.lat2;
            lastCoordinate.lng=g.lon2;
            resultCoordinates.add(new LatLng(g.lat2,g.lon2));
//            System.out.println(g.lat2 + "," + g.lon2);
        }
        else{
            offset=offset-line.Distance();
            return;
        }

        // The number of intervals
        int num = (int)(Math.floor(line.Distance() / requiredMinDistance));

        for (int i=1; i<=num; ++i) {
            GeodesicData g=line.Position(i * requiredMinDistance, GeodesicMask.LATITUDE | GeodesicMask.LONGITUDE);
            lastCoordinate.lat=g.lat2;
            lastCoordinate.lng=g.lon2;
            resultCoordinates.add(new LatLng(g.lat2,g.lon2));
//            System.out.println(g.lat2 + "," + g.lon2);
        }
        offset=requiredMinDistance-geod.InverseLine(lastCoordinate.lat, lastCoordinate.lng, lat2, lon2,
                GeodesicMask.DISTANCE_IN | GeodesicMask.LATITUDE | GeodesicMask.LONGITUDE).Distance();
    }
}
