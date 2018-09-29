package com.example.sauravrp.listings.repo;

import android.content.Context;
import android.content.SharedPreferences;

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

    @Inject
    public StorageRepo( @Named("app_context") Context appContext) {
        this.appContext = appContext;
        sharedPreferences = this.appContext.getSharedPreferences(SHARED_PREFS_FILE_NAME, MODE_PRIVATE);
    }

    @Override
    public Set<String> getFavorites() {
        return sharedPreferences.getStringSet(FAV_KEY, new TreeSet<>());
    }

    @Override
    public void addFavorite(String id) {
        Set<String> data = getFavorites();
        data.add(id);
        sharedPreferences.edit().putStringSet(FAV_KEY, data).apply();
    }

    @Override
    public void removeFavorite(String id) {
        Set<String> data = getFavorites();
        data.remove(id);
        sharedPreferences.edit().putStringSet(FAV_KEY, data).apply();
    }
}
