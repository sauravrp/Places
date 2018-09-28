package com.example.sauravrp.listings.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResultData {

    @SerializedName("venues")
    private ArrayList<Listing> listings;

    public ArrayList<Listing> getListings() {
        return listings;
    }

    public void setListings(ArrayList<Listing> listings) {
        this.listings = listings;
    }
}
