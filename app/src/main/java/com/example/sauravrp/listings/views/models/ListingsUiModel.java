package com.example.sauravrp.listings.views.models;

import java.io.Serializable;

public class ListingsUiModel implements Serializable {

    private String id;

    private String name;


    public ListingsUiModel(String id, String title) {
        this.id = id;
        this.name = title;
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
}
