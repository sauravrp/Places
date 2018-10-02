package com.example.sauravrp.listings.viewmodels.models;

import java.util.Objects;

public class ListingsUiDetailModel extends ListingsUiModel {

    private String url;
    private String phone;

    ListingsAddressUiModel address;


    public ListingsUiDetailModel(String id, String name) {
        super(id, name);
    }

    public ListingsUiDetailModel(ListingsUiModel model) {
        super(model.getId(), model.getName());
        setCategory(model.getCategory());
        setIconUrl(model.getIconUrl());
        setDistance(model.getDistance());
        setLatitude(model.getLatitude());
        setLongitude(model.getLongitude());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        ListingsUiDetailModel that = (ListingsUiDetailModel) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(url, phone, address);
    }

    @Override
    public String toString() {
        return "ListingsUiDetailModel{" +
                "url='" + url + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }
}
