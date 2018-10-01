package com.example.sauravrp.listings.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
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

    public ListingDetailViewModel(ILocationService locationService, IDataModel dataModel, IStorageModel storageModel) {
        this.locationService = locationService;
        this.dataModel = dataModel;
        this.storageModel = storageModel;
    }

    public Location getUserLocation() {
       return this.locationService.getUserLocation();
    }

    private final MutableLiveData<String> phoneNumberSelected = new MutableLiveData<>();

    private final MutableLiveData<ListingsUiModel> addressSelected = new MutableLiveData<>();

    public void phoneNumberSelected(String number) {
        phoneNumberSelected.setValue(number);
    }

    public void addressSelected(ListingsUiModel address) {
        addressSelected.setValue(address);
    }

    public LiveData<String> getSelectedPhoneNumber() {
        return phoneNumberSelected;
    }

    public LiveData<ListingsUiModel> getSelectedAddress() {
        return addressSelected;
    }

    public void setSelection(ListingsUiModel sel) {
        ListingsUiDetailModel detailModel = new ListingsUiDetailModel(sel);
        selection.setValue(detailModel);

        dataModel.getListingDetail(sel.getId()).subscribe(listingDetail -> {
            boolean isFavorited = storageModel.getFavorites().contains(listingDetail.getId());
            float distance = locationService.distanceFromInMiles(listingDetail.getLocation().getLat(), listingDetail.getLocation().getLng());
            ListingsUiDetailModel data = ModelConverters.createListingsUiDetailModel(listingDetail, distance, isFavorited);
                selection.setValue(data);
            }, e-> {
            // better option woudl be  to send this UI
            Log.e(TAG, e.toString());
        });
    }

    public LiveData<ListingsUiDetailModel> getSelection() {
        return selection;
    }
}
