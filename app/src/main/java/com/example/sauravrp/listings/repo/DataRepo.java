package com.example.sauravrp.listings.repo;

import com.example.sauravrp.listings.helpers.interfaces.ISchedulerProvider;
import com.example.sauravrp.listings.network.FoursquareAPI;
import com.example.sauravrp.listings.network.models.Listing;
import com.example.sauravrp.listings.network.models.ListingDetail;
import com.example.sauravrp.listings.repo.interfaces.IDataModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class DataRepo implements IDataModel {

    private final static int FETCH_SIZE = 50;

    private final static String CLIENT_ID = "REPLACE_WITH_YOURS";
    private final static String CLIENT_SECRET = "REPLACE_WITH_YOURS";
    private final static String VERSION  = "20180928";

    private final FoursquareAPI foursquareAPI;
    private final ISchedulerProvider schedulerProvider;

    public DataRepo(FoursquareAPI yahooAPI, ISchedulerProvider aSchedulerProvider) {
        this.foursquareAPI = yahooAPI;
        this.schedulerProvider = aSchedulerProvider;
    }

    @Override
    public Single<List<Listing>> getListings(final String city, final String query) {
        return foursquareAPI.searchListings(CLIENT_ID, CLIENT_SECRET, city, VERSION, query, FETCH_SIZE)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .flatMap(resultQuery -> {
                            ArrayList<Listing> results;
                            if (resultQuery != null
                                    && resultQuery.getResponse() != null
                                    && resultQuery.getResponse().getListings() != null) {
                                results = resultQuery.getResponse().getListings();
                            } else {
                                results = new ArrayList<>();
                            }
                            return Single.just(results);
                        }
                );
    }

    @Override
    public Single<ListingDetail> getListingDetail(final String id) {
        return foursquareAPI.getListing(id, CLIENT_ID, CLIENT_SECRET, VERSION)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .flatMap(resultQuery -> Single.just(resultQuery.getResponse().getListingDetail()));
    }
}
