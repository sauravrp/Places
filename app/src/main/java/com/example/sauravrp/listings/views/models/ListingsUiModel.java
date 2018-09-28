package com.example.sauravrp.listings.views.models;

import java.io.Serializable;
import java.util.Objects;

public class ListingsUiModel implements Serializable {

    private String id;

    private String title;

    private ListingsAddressUiModel address;

    private String phone;

    private String distance;


    public ListingsUiModel(String id, String title, String address, String city, String state, String phone, String distance) {
        this.id = id;
        this.title = title;
        this.address = new ListingsAddressUiModel(address, city, state);
        this.phone = phone;
        this.distance = distance;
    }

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

    public ListingsAddressUiModel getAddress() {
        return address;
    }

    public void setAddress(ListingsAddressUiModel address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingsUiModel that = (ListingsUiModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(address, that.address) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(distance, that.distance);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, address, phone, distance);
    }

    @Override
    public String toString() {
        return "ListingsUiModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
