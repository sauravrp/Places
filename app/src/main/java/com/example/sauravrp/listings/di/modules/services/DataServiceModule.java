package com.example.sauravrp.listings.di.modules.services;

import android.content.Context;

import com.example.sauravrp.listings.network.FoursquareAPI;
import com.example.sauravrp.listings.repo.DataRepo;
import com.example.sauravrp.listings.repo.StorageRepo;
import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.helpers.interfaces.ISchedulerProvider;
import com.example.sauravrp.listings.repo.interfaces.IStorageModel;

import javax.inject.Named;

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

    @Provides
    IStorageModel providesStorageModel(@Named("app_context") Context ctx) {
        return new StorageRepo(ctx);
    }
}
