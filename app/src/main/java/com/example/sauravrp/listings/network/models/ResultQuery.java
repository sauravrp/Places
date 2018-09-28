package com.example.sauravrp.listings.network.models;

import com.google.gson.annotations.SerializedName;

public class ResultQuery<T> {

    @SerializedName("response")
    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
