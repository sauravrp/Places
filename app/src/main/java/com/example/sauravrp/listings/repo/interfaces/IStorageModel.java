package com.example.sauravrp.listings.repo.interfaces;

import android.databinding.ObservableList;

public interface IStorageModel {
    ObservableList<String> getFavorites();
    void addFavorite(String id);
    void removeFavorite(String id);
}
