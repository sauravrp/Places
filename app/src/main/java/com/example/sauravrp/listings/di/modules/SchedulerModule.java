package com.example.sauravrp.listings.di.modules;

import com.example.sauravrp.listings.helpers.SchedulerProvider;
import com.example.sauravrp.listings.helpers.interfaces.ISchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulerModule {

   private ISchedulerProvider schedulerProvider;

    @Provides
    ISchedulerProvider provider() {
        if(schedulerProvider == null) {
            schedulerProvider = new SchedulerProvider();
        }
        return schedulerProvider;
    }
}
