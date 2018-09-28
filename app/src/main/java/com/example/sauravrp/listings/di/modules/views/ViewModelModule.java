package com.example.sauravrp.listings.di.modules.views;

import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.viewmodels.ListingDetailViewModel;
import com.example.sauravrp.listings.viewmodels.ListingsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    ListingsViewModel providesListingsViewModel(IDataModel dataModel) {
        return new ListingsViewModel(dataModel);
    }

    @Provides
    ListingDetailViewModel providesListingDetailViewModel() {
        return new ListingDetailViewModel();
    }
}
