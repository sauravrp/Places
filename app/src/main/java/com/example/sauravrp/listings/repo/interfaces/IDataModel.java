package com.example.sauravrp.listings.repo.interfaces;

import com.example.sauravrp.listings.network.models.Listing;
import com.example.sauravrp.listings.network.models.ListingDetail;

import java.util.List;

import io.reactivex.Single;

public interface IDataModel {
    Single<List<Listing>> getListings(String query);
    Single<ListingDetail> getListingDetail(String id);
}
