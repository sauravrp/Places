package com.example.sauravrp.listings.di.modules.views;

import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.repo.interfaces.IStorageModel;
import com.example.sauravrp.listings.service.interfaces.ILocationService;
import com.example.sauravrp.listings.viewmodels.ListingDetailViewModel;
import com.example.sauravrp.listings.viewmodels.ListingsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    ListingsViewModel providesListingsViewModel(ILocationService locationService, IDataModel dataModel, IStorageModel storageModel) {
        return new ListingsViewModel(locationService, dataModel, storageModel);
    }

    @Provides
    ListingDetailViewModel providesListingDetailViewModel() {
        return new ListingDetailViewModel();
    }
}
