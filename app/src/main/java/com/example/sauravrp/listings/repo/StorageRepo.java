package com.example.sauravrp.listings.repo;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.example.sauravrp.listings.repo.interfaces.IStorageModel;

import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;

import static android.content.Context.MODE_PRIVATE;

public class StorageRepo implements IStorageModel {

    private static String SHARED_PREFS_FILE_NAME = "StorageRepo";
    private static String FAV_KEY = "FAVORITES";

    Context appContext;

    private SharedPreferences sharedPreferences;

    private ObservableArrayList<String> favoriteIds;

    @Inject
    public StorageRepo( @Named("app_context") Context appContext) {
        this.appContext = appContext;
        sharedPreferences = this.appContext.getSharedPreferences(SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
    }

    private void initFavorites() {
        Set<String> ids = sharedPreferences.getStringSet(FAV_KEY, new TreeSet<>());
        favoriteIds = new ObservableArrayList<>();
        favoriteIds.addAll(ids);
    }

    @Override
    public ObservableList<String> getFavorites() {
        if(favoriteIds == null) {
            initFavorites();
        }
        return favoriteIds;
    }

    @Override
    public void addFavorite(String id) {
        if(favoriteIds == null) {
            initFavorites();
        }
        if(!favoriteIds.contains(id)) {
            favoriteIds.add(id);
        }
        writeToSharedPrefs();
    }

    @Override
    public void removeFavorite(String id) {
        if(favoriteIds == null) {
            initFavorites();
        }

        if(favoriteIds.contains(id)) {
            favoriteIds.remove(id);
        }
        writeToSharedPrefs();
    }

    private void writeToSharedPrefs() {
        TreeSet<String> data = new TreeSet<>(favoriteIds);
        sharedPreferences.edit().putStringSet(FAV_KEY, data).apply();

    }


}
