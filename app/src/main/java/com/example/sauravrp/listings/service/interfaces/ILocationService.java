package com.example.sauravrp.listings.service.interfaces;

import com.example.sauravrp.listings.service.models.Location;

public interface ILocationService {

    Location getLocation();
    float distanceFromInMiles(double latitude, double longitude);

}
