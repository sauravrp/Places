package com.example.sauravrp.listings.di.modules.services;

import com.example.sauravrp.listings.network.FoursquareAPI;
import com.example.sauravrp.listings.repo.DataRepo;
import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.helpers.interfaces.ISchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class DataServiceModule {

    private DataRepo yahooDataService;

    @Provides
    IDataModel providesDataModel(FoursquareAPI yahooAPI, ISchedulerProvider schedulerProvider) {
        if(yahooDataService == null) {
            yahooDataService = new DataRepo(yahooAPI, schedulerProvider);
        }
        return yahooDataService;
    }
}
