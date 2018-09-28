package com.example.sauravrp.listings.repo;

import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.helpers.interfaces.ISchedulerProvider;
import com.example.sauravrp.listings.network.models.unused.Listing;
import com.example.sauravrp.listings.network.YahooAPI;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class DataRepo implements IDataModel {

    private final static int FETCH_SIZE = 15;

    private final YahooAPI yahooAPI;
    private final ISchedulerProvider schedulerProvider;

    public DataRepo(YahooAPI yahooAPI, ISchedulerProvider aSchedulerProvider) {
        this.yahooAPI = yahooAPI;
        this.schedulerProvider = aSchedulerProvider;
    }

    @Override
    public Single<List<Listing>> getListings(double latitude, double longitude, int offset) {
        String query = String.format("select * from local.search(%d,%d) where latitude='%f' and longitude='%f' and query='pizza'", offset, FETCH_SIZE, latitude, longitude);
        return yahooAPI.getQueryResults(query, "json", true, "")
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .flatMap(resultQuery -> {
                            ArrayList<Listing> results;
                            if (resultQuery != null
                                    && resultQuery.getQueryInfo() != null
                                    && resultQuery.getQueryInfo().getResults() != null
                                    && resultQuery.getQueryInfo().getResults().getListings() != null) {
                                results = resultQuery.getQueryInfo().getResults().getListings();
                            } else {
                                results = new ArrayList<>();
                            }
                            return Single.just(results);
                        }
                );
    }
}
