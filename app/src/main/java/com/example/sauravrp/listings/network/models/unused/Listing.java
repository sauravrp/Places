package com.example.sauravrp.listings.network.models.unused;

import com.google.gson.annotations.SerializedName;

public class Listing {
    private String id;

    @SerializedName("Title")
    private String title;

    @SerializedName("Address")
    private String address;

    @SerializedName("City")
    private String city;

    @SerializedName("State")
    private String state;

    @SerializedName("Phone")
    private String phone;

    @SerializedName("Distance")
    private String distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
