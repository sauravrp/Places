package com.example.sauravrp.listings.di.modules.service;

import com.example.sauravrp.listings.service.LocationService;
import com.example.sauravrp.listings.service.interfaces.ILocationService;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationModule {
    @Provides
    ILocationService providesLocationService() {
        return new LocationService();
    }
}
