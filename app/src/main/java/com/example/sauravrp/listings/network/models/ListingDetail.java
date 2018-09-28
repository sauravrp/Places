package com.example.sauravrp.listings.network.models;

import com.google.gson.annotations.SerializedName;

public class ListingDetail extends Listing {
    @SerializedName("canonicalUrl")
    private String url;

    private Contact contact;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
