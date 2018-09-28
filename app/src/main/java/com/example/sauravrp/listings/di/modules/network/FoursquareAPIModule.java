package com.example.sauravrp.listings.di.modules.network;

import com.example.sauravrp.listings.network.FoursquareAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FoursquareAPIModule {

    private FoursquareAPI foursquareAPI;

    @Provides
    FoursquareAPI providesYahooAPI(Retrofit retrofit) {
        if(foursquareAPI == null) {
            foursquareAPI = retrofit.create(FoursquareAPI.class);
        }
        return foursquareAPI;
    }
}
