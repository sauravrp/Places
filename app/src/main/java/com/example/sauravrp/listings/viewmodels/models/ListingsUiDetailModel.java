package com.example.sauravrp.listings.viewmodels.models;

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
}
