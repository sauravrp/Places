package com.example.sauravrp.listings.network.models;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("shortName")
    private String name;
    private Icon icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
