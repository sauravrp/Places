package com.example.sauravrp.listings.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.util.Pair;

import com.example.sauravrp.listings.network.models.unused.Listing;
import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.viewmodels.models.Location;
import com.example.sauravrp.listings.views.models.ListingsUiModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class ListingsViewModel extends ViewModel {

    private final IDataModel dataModel;

    private Location location;

    private final PublishSubject<Pair<Location, Integer>> listingsSubject = PublishSubject.create();
    private final MutableLiveData<ListingsUiModel> listingSelected = new MutableLiveData<>();

    public ListingsViewModel(IDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public Observable<List<ListingsUiModel>> getListings() {
        return listingsSubject
                .flatMap(pair -> dataModel.getListings(pair.first.getLatitude(),
                        pair.first.getLongitude(),
                        pair.second).toObservable())
                .flatMap(list -> Observable.just(createUiModel(list)));
    }

    public LiveData<ListingsUiModel> getSelectedListing() {
        return listingSelected;
    }


    public void getMoreListings(final Location location, final int offset) {
        if(location != null) {
            this.location = location;
            listingsSubject.onNext(Pair.create(location, offset));
        }
    }

    public void getMoreListings(final int offset) {
        if(location != null) {
            listingsSubject.onNext(Pair.create(location, offset));
        }
    }

    public void selectListing(final ListingsUiModel resultUiModel) {
        listingSelected.setValue(resultUiModel);
    }

    private List<ListingsUiModel> createUiModel(List<Listing> results) {
        ArrayList<ListingsUiModel> newList = new ArrayList<>();
        for (Listing item : results) {
            newList.add(new ListingsUiModel(item.getId(),
                    item.getTitle(),
                    item.getAddress(),
                    item.getCity(),
                    item.getState(),
                    item.getPhone(),
                    item.getDistance()));
        }

        return newList;
    }
}
