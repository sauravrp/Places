package com.example.sauravrp.listings.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Named("app_context")
    Context providesAppContext() {
        return  application.getApplicationContext();
    }
}
