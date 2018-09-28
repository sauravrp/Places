package com.example.sauravrp.listings.network.models.unused;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultData {

    @SerializedName("Result")
    private ArrayList<Listing> listings;

    public ArrayList<Listing> getListings() {
        return listings;
    }

    public void setListings(ArrayList<Listing> listings) {
        this.listings = listings;
    }
}
