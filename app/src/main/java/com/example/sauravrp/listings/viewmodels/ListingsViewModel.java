package com.example.sauravrp.listings.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.example.sauravrp.listings.network.models.Category;
import com.example.sauravrp.listings.network.models.Listing;
import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.repo.interfaces.IStorageModel;
import com.example.sauravrp.listings.service.interfaces.ILocationService;
import com.example.sauravrp.listings.views.models.ListingsUiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class ListingsViewModel extends ViewModel {

    private static final int ICON_SIZE = 64;
    private static final String BACKGROUND_GREY = "bg_";

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

    public Observable<List<ListingsUiModel>> getListings() {
        return listingsSubject
                .flatMap(query -> dataModel.getListings(locationService.getLocation().getCurrentLocation(), query).toObservable())
                .flatMap(list -> Observable.just(createUiModel(list)));
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

    private List<ListingsUiModel> createUiModel(List<Listing> results) {
       Set<String> favorites = storageModel.getFavorites();
        ArrayList<ListingsUiModel> newList = new ArrayList<>();
        for (Listing item : results) {
            ListingsUiModel uiModel = new ListingsUiModel(item.getId(), item.getName());
            uiModel.setFavorite(favorites.contains(uiModel.getId()));
            if(item.getCategories() != null && item.getCategories().size() > 0) {
                Category category = item.getCategories().get(0);
                uiModel.setCategory(category.getName());
                if(!TextUtils.isEmpty(category.getIcon().getPrefix()) &&
                        !TextUtils.isEmpty(category.getIcon().getSuffix())) {
                    uiModel.setIconUrl(category.getIcon().getPrefix() + BACKGROUND_GREY + Integer.toString(ICON_SIZE) + category.getIcon().getSuffix());
                }
            }
            uiModel.setDistance(locationService.distanceFromInMiles(item.getLocation().getLat(), item.getLocation().getLng()));

            newList.add(uiModel);
        }

        return newList;
    }
}
