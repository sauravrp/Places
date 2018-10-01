package com.example.sauravrp.listings.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sauravrp.listings.service.interfaces.ILocationService;
import com.example.sauravrp.listings.service.models.Location;
import com.example.sauravrp.listings.views.models.ListingsUiModel;

public class ListingDetailViewModel extends ViewModel {

    private ListingsUiModel selection;
    private ILocationService locationService;

    public ListingDetailViewModel(ILocationService locationService) {
        this.locationService = locationService;
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

    public void setSelection(ListingsUiModel selection) {
        this.selection = selection;
    }

    public ListingsUiModel getSelection() {
        return selection;
    }
}
