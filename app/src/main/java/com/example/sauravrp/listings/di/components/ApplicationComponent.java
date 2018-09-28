package com.example.sauravrp.listings.di.components;

import com.example.sauravrp.listings.ListingsApplication;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import com.example.sauravrp.listings.di.modules.SchedulerModule;
import com.example.sauravrp.listings.di.modules.network.RetrofitModule;
import com.example.sauravrp.listings.di.modules.network.FoursquareAPIModule;
import com.example.sauravrp.listings.di.modules.services.DataServiceModule;
import com.example.sauravrp.listings.di.modules.views.ActivityBuilderModule;
import com.example.sauravrp.listings.di.modules.views.ViewModelModule;

@Component(modules = {AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        // these can be in NetworkComponent if need be
        RetrofitModule.class, FoursquareAPIModule.class,
        // data service layer
        DataServiceModule.class,
        // view model
        ViewModelModule.class,
        // helper
        SchedulerModule.class})
public abstract class ApplicationComponent {

    public abstract void inject(ListingsApplication app);
}
