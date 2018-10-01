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

    private final static int FETCH_SIZE = 15;

    private final static String CLIENT_ID = "LFYPFZ51PUQ2DZTMELQFHKEZTWA1MXJUJLF1WCOCZ4RM2HDU";
    private final static String CLIENT_SECRET = "QM40YP5GZW3GBLARSIKII3LKZUK054F0CK10TQJDFCRNSUA0";
    private final static String VERSION  = "20180928";

    private final FoursquareAPI yahooAPI;
    private final ISchedulerProvider schedulerProvider;

    public DataRepo(FoursquareAPI yahooAPI, ISchedulerProvider aSchedulerProvider) {
        this.yahooAPI = yahooAPI;
        this.schedulerProvider = aSchedulerProvider;
    }

    @Override
    public Single<List<Listing>> getListings(final String city, final String query) {
        return yahooAPI.searchListings(CLIENT_ID, CLIENT_SECRET, city, VERSION, query, FETCH_SIZE)
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
        return yahooAPI.getListing(CLIENT_ID, CLIENT_SECRET, VERSION, id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .flatMap(resultQuery -> Single.just(resultQuery.getResponse().getListingDetail()));
    }
}
