package com.example.sauravrp.listings.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableList;
import android.util.Log;

import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.repo.interfaces.IStorageModel;
import com.example.sauravrp.listings.service.interfaces.ILocationService;
import com.example.sauravrp.listings.service.models.Location;
import com.example.sauravrp.listings.viewmodels.helper.ModelConverters;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiDetailModel;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiModel;

public class ListingDetailViewModel extends ViewModel {


    private final static String TAG = "ListingDetailViewModel";

    private final IDataModel dataModel;
    private final IStorageModel storageModel;
    private final ILocationService locationService;

    private MutableLiveData<ListingsUiDetailModel> selection = new MutableLiveData<>();

    private final MutableLiveData<String> phoneNumberSelected = new MutableLiveData<>();

    private final MutableLiveData<ListingsUiDetailModel> addressSelected = new MutableLiveData<>();

    private final MutableLiveData<String> webUrlSelected = new MutableLiveData<>();

    public ListingDetailViewModel(ILocationService locationService, IDataModel dataModel, IStorageModel storageModel) {
        this.locationService = locationService;
        this.dataModel = dataModel;
        this.storageModel = storageModel;
    }

    public ObservableList<String> getFavorites() {
        return storageModel.getFavorites();
    }


    public Location getUserLocation() {
       return this.locationService.getUserLocation();
    }


    public void phoneNumberSelected(String number) {
        phoneNumberSelected.setValue(number);
    }

    public void addressSelected(ListingsUiDetailModel address) {
        addressSelected.setValue(address);
    }

    public void websiteSelected(String url) {
        webUrlSelected.setValue(url);
    }

    public void favorite() {
        storageModel.addFavorite(selection.getValue().getId());
    }

    public void unFavorite() {
        storageModel.removeFavorite(selection.getValue().getId());
    }

    public LiveData<String> getSelectedPhoneNumber() {
        return phoneNumberSelected;
    }

    public LiveData<ListingsUiDetailModel> getSelectedAddress() {
        return addressSelected;
    }

    public LiveData<String> getSelectedWebSite() {
        return webUrlSelected;
    }

    public void setSelection(ListingsUiModel sel) {
        ListingsUiDetailModel detailModel = new ListingsUiDetailModel(sel);
        selection.setValue(detailModel);

        dataModel.getListingDetail(sel.getId()).subscribe(listingDetail -> {
             float distance = locationService.distanceFromInMiles(listingDetail.getLocation().getLat(), listingDetail.getLocation().getLng());
            ListingsUiDetailModel data = ModelConverters.createListingsUiDetailModel(listingDetail, distance);
                selection.setValue(data);
            }, e-> {
            // better option would be  to send this UI
            Log.e(TAG, e.toString());
        });
    }

    public LiveData<ListingsUiDetailModel> getSelection() {
        return selection;
    }
}
