package com.example.sauravrp.listings.di.components;

import com.example.sauravrp.listings.ListingsApplication;
import com.example.sauravrp.listings.di.modules.ApplicationModule;
import com.example.sauravrp.listings.di.modules.SchedulerModule;
import com.example.sauravrp.listings.di.modules.network.FoursquareAPIModule;
import com.example.sauravrp.listings.di.modules.network.RetrofitModule;
import com.example.sauravrp.listings.di.modules.services.DataServiceModule;
import com.example.sauravrp.listings.di.modules.views.ActivityBuilderModule;
import com.example.sauravrp.listings.di.modules.views.ViewModelModule;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        // app context module
        ApplicationModule.class,
        // these can be in NetworkComponent if need be
        RetrofitModule.class, FoursquareAPIModule.class,
        // data service layer
        DataServiceModule.class,
        // view model
        ViewModelModule.class,
        // helper
        SchedulerModule.class})
public interface ApplicationComponent {

    void inject(ListingsApplication app);

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
        Builder applicationModule(ApplicationModule m);
    }
}
