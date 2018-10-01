package com.example.sauravrp.listings;

import android.app.Activity;
import android.app.Application;

import com.example.sauravrp.listings.di.components.ApplicationComponent;
import com.example.sauravrp.listings.di.components.DaggerApplicationComponent;
import com.example.sauravrp.listings.di.modules.ApplicationModule;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class ListingsApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationComponent component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        component.inject(this);
        Fresco.initialize(this);
        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
