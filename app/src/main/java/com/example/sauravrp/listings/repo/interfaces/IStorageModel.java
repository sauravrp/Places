package com.example.sauravrp.listings.repo.interfaces;

import java.util.Set;

public interface IStorageModel {
    Set<String> getFavorites();
    void addFavorite(String id);
    void removeFavorite(String id);
}
