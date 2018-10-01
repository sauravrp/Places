package com.example.sauravrp.listings.service.models;

public class Location {
    private String currentLocation;
    private double latitiude;
    private double longitude;

    public Location(String currentLocation, double latitiude, double longitude) {
        this.currentLocation = currentLocation;
        this.latitiude = latitiude;
        this.longitude = longitude;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public double getLatitiude() {
        return latitiude;
    }

    public void setLatitiude(double latitiude) {
        this.latitiude = latitiude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
