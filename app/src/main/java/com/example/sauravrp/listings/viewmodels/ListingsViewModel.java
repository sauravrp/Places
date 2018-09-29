package com.example.sauravrp.listings.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sauravrp.listings.network.models.Listing;
import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.repo.interfaces.IStorageModel;
import com.example.sauravrp.listings.views.models.ListingsUiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class ListingsViewModel extends ViewModel {

    private final IDataModel dataModel;
    private final IStorageModel storageModel;

    private final PublishSubject<String> listingsSubject = PublishSubject.create();
    private final MutableLiveData<ListingsUiModel> listingSelected = new MutableLiveData<>();

    public ListingsViewModel(IDataModel dataModel, IStorageModel storageModel) {
        this.dataModel = dataModel;
        this.storageModel = storageModel;
    }

    public Observable<List<ListingsUiModel>> getListings() {
        return listingsSubject
                .flatMap(query -> dataModel.getListings(query).toObservable())
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
            uiModel.setFavorited(favorites.contains(uiModel.getId()));
            newList.add(uiModel);
        }

        return newList;
    }
}
