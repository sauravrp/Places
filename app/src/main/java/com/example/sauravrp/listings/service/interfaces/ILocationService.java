package com.example.sauravrp.listings.service.interfaces;

import com.example.sauravrp.listings.service.models.Location;

public interface ILocationService {

    Location getUserLocation();
    float distanceFromInMiles(double latitude, double longitude);

}
