package com.example.sauravrp.listings.service;

import com.example.sauravrp.listings.service.interfaces.ILocationService;
import com.example.sauravrp.listings.service.models.Location;

public class LocationService implements ILocationService {

    private final static String CITY = "Seattle,WA";
    private final double LATITUDE = 47.606200;
    private final double LONGITUDE = -122.332100;

    private final float METER_TO_MILES = 1609.344f;

    private Location mylocation;

    public LocationService() {
        mylocation = new Location(CITY, LATITUDE, LONGITUDE);
    }

    @Override
    public Location getUserLocation() {
        return mylocation;
    }

    @Override
    public float distanceFromInMiles(double latitude, double longitude) {
        float result = 0.0f;
        float[] results = new float[1];
        android.location.Location.distanceBetween(mylocation.getLatitiude(), mylocation.getLongitude(),
                                                            latitude, longitude,
                                                            results);

        if(results.length > 0) {
            result = results[0];
        }
        return result/ METER_TO_MILES;
    }
}
