package com.example.sauravrp.listings.viewmodels.models;

import java.io.Serializable;
import java.util.Objects;

public class ListingsUiModel implements Serializable {

    private String id;

    private String name;

    private String category;

    private String iconUrl;

    private float distance;

    private double latitude;

    private double longitude;

    public ListingsUiModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingsUiModel that = (ListingsUiModel) o;
        return Float.compare(that.distance, distance) == 0 &&
                Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(category, that.category) &&
                Objects.equals(iconUrl, that.iconUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, category, iconUrl, distance, latitude, longitude);
    }

    @Override
    public String toString() {
        return "ListingsUiModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", distance=" + distance +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
