package com.example.sauravrp.listings.viewmodels.models;

import java.io.Serializable;
import java.util.Objects;

public class ListingsAddressUiModel implements Serializable {

    private String street;

    private String city;

    private String state;

    public ListingsAddressUiModel(String address, String city, String state) {
        this.street = address;
        this.city = city;
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingsAddressUiModel that = (ListingsAddressUiModel) o;
        return Objects.equals(street, that.street) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {

        return Objects.hash(street, city, state);
    }

    @Override
    public String toString() {
        return "ListingsAddressUiModel{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
