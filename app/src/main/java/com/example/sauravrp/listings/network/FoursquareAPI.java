package com.example.sauravrp.listings.network;

import com.example.sauravrp.listings.network.models.DetailResultData;
import com.example.sauravrp.listings.network.models.ResultQuery;
import com.example.sauravrp.listings.network.models.SearchResultData;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoursquareAPI {

    @GET("v2/venues/search")
    Single<ResultQuery<SearchResultData>> searchListings(@Query("client_id") String clientID,
                                                         @Query("client_secret") String clientSecret,
                                                         @Query("near") String cityName,
                                                         @Query("v") String version,
                                                         @Query("query") String query,
                                                         @Query("limit") int limit);

    @GET("v2/venues/{venueId}")
    Single<ResultQuery<DetailResultData>> getListing(@Query("client_id") String clientID,
                                                          @Query("client_secret") String clientSecret,
                                                          @Query("v") String version,
                                                          @Path("venueId") String id);
}
