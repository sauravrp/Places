package com.example.sauravrp.listings.network.models;

import com.google.gson.annotations.SerializedName;

public class DetailResultData {
    @SerializedName("venue")
    private ListingDetail listingDetail;

    public ListingDetail getListingDetail() {
        return listingDetail;
    }

    public void setListingDetail(ListingDetail listingDetail) {
        this.listingDetail = listingDetail;
    }
}
