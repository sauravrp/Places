package com.example.sauravrp.listings.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableList;

import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.repo.interfaces.IStorageModel;
import com.example.sauravrp.listings.service.interfaces.ILocationService;
import com.example.sauravrp.listings.service.models.Location;
import com.example.sauravrp.listings.viewmodels.helper.ModelConverters;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class ListingsViewModel extends ViewModel {

   private final IDataModel dataModel;
    private final IStorageModel storageModel;
    private final ILocationService locationService;

    private final PublishSubject<String> listingsSubject = PublishSubject.create();
    private final MutableLiveData<ListingsUiModel> listingSelected = new MutableLiveData<>();

    public ListingsViewModel(ILocationService locationService, IDataModel dataModel, IStorageModel storageModel) {
        this.locationService = locationService;
        this.dataModel = dataModel;
        this.storageModel = storageModel;
    }

    public ObservableList<String> getFavorites() {
        return storageModel.getFavorites();
    }

    public Location getUserLocation() {
        return locationService.getUserLocation();
    }

    public Observable<List<ListingsUiModel>> getListings() {
        return listingsSubject
                .flatMap(query -> dataModel.getListings(locationService.getUserLocation().getCurrentLocation(), query).toObservable())
                .flatMap(list -> Observable.just(ModelConverters.createListingsUiModels(list, locationService)));
    }

    public LiveData<ListingsUiModel> getSelectedListing() {
        return listingSelected;
    }

    public void searchListings(final String query) {
        if(query != null) {
            listingsSubject.onNext(query);
        }
    }

    public void selectListing(final ListingsUiModel resultUiModel) {
        listingSelected.setValue(resultUiModel);
    }

}
