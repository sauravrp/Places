package com.example.sauravrp.listings.di.modules.network;

import com.example.sauravrp.listings.network.YahooAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class YahooAPIModule {

    private YahooAPI yahooAPI;

    @Provides
    YahooAPI providesYahooAPI(Retrofit retrofit) {
        if(yahooAPI == null) {
            yahooAPI = retrofit.create(YahooAPI.class);
        }
        return yahooAPI;
    }
}
