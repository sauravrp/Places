package com.example.sauravrp.listings.di.modules.views;

import com.example.sauravrp.listings.views.MapActivity;
import com.example.sauravrp.listings.views.ListingDetailActivity;
import com.example.sauravrp.listings.views.ListingsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract ListingsActivity bindListingsActivity();

    @ContributesAndroidInjector
    abstract ListingDetailActivity bindListingDetailActivity();

    @ContributesAndroidInjector
    abstract MapActivity bindMapActivity();
}
