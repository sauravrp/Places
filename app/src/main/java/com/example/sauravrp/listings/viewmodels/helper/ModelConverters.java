package com.example.sauravrp.listings.viewmodels.helper;

import android.text.TextUtils;

import com.example.sauravrp.listings.network.models.Category;
import com.example.sauravrp.listings.network.models.Listing;
import com.example.sauravrp.listings.network.models.ListingDetail;
import com.example.sauravrp.listings.service.interfaces.ILocationService;
import com.example.sauravrp.listings.viewmodels.models.ListingsAddressUiModel;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiDetailModel;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiModel;

import java.util.ArrayList;
import java.util.List;

public class ModelConverters {

    private static final int ICON_SIZE = 64;
    private static final String BACKGROUND_GREY = "bg_";

    public static List<ListingsUiModel> createListingsUiModels(final List<Listing> results, ILocationService service) {
        ArrayList<ListingsUiModel> newList = new ArrayList<>();
        for (Listing item : results) {
            float distance = service.distanceFromInMiles(item.getLocation().getLat(), item.getLocation().getLng());
            newList.add(createListingsUiModel(item, distance));
        }
        return newList;
    }

    public static ListingsUiModel createListingsUiModel(final Listing result, float distance) {

        ListingsUiModel uiModel = new ListingsUiModel(result.getId(), result.getName());
        if(result.getCategories() != null && result.getCategories().size() > 0) {
            Category category = result.getCategories().get(0);
            uiModel.setCategory(category.getName());
            if(!TextUtils.isEmpty(category.getIcon().getPrefix()) &&
                    !TextUtils.isEmpty(category.getIcon().getSuffix())) {
                uiModel.setIconUrl(category.getIcon().getPrefix() + BACKGROUND_GREY + Integer.toString(ICON_SIZE) + category.getIcon().getSuffix());
            }
        }
        uiModel.setLatitude(result.getLocation().getLat());
        uiModel.setLongitude(result.getLocation().getLng());
        uiModel.setDistance(distance);
        return uiModel;
    }

    public static ListingsUiDetailModel createListingsUiDetailModel(final ListingDetail result, float distance) {
        ListingsUiDetailModel detailModel = new ListingsUiDetailModel(createListingsUiModel(result, distance));
        detailModel.setUrl(result.getUrl());
        detailModel.setPhone(result.getContact().getFormattedPhone());
        detailModel.setAddress(new ListingsAddressUiModel(result.getLocation().getAddress(),
                result.getLocation().getCity(), result.getLocation().getState()));
        return detailModel;
    }
}
