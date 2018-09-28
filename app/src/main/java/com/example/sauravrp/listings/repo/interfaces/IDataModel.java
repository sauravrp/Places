package com.example.sauravrp.listings.repo.interfaces;

import com.example.sauravrp.listings.network.models.unused.Listing;

import java.util.List;

import io.reactivex.Single;

public interface IDataModel {
    Single<List<Listing>> getListings(double latitude, double longitude, int offset);
}
