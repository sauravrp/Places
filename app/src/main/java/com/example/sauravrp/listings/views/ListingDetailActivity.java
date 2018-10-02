package com.example.sauravrp.listings.views;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.sauravrp.listings.BR;
import com.example.sauravrp.listings.R;
import com.example.sauravrp.listings.helpers.IntentHelper;
import com.example.sauravrp.listings.viewmodels.ListingDetailViewModel;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiDetailModel;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class ListingDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final static String TAG = "ListingDetailActivity";
    private final static String SELECTION = "selection";

    public static void startActivity(Context ctx, ListingsUiModel selected) {
        Intent intent = new Intent(ctx, ListingDetailActivity.class);
        intent.putExtra(SELECTION, selected);
        ctx.startActivity(intent);
    }

    @Inject
    ListingDetailViewModel viewModel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private GoogleMap googleMap;

    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_listing_detail);
        binding.setLifecycleOwner(this);

        ButterKnife.bind(this);

        viewModel.setSelection(getSelectionFromBundle());
        binding.setVariable(BR.viewModel, viewModel);

        setSupportActionBar(toolbar);
        setTitle("");

        toolbar.setNavigationIcon(R.drawable.outline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        viewModel.getSelectedAddress().observe(this, this::gotoAddress);
        viewModel.getSelectedPhoneNumber().observe(this, this::callPhoneNumber);
        viewModel.getSelectedWebSite().observe(this, this::gotoWebsite);

        setupGoogleMap();
    }

    private void setupGoogleMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.details_map);
        mapFragment.getMapAsync(this);
    }

    private ListingsUiModel getSelectionFromBundle() {
        Bundle extras = getIntent().getExtras();
        return (ListingsUiModel) extras.getSerializable(SELECTION);
    }

    private void callPhoneNumber(String number) {
        if(!TextUtils.isEmpty(number)) {
            IntentHelper.launchPhone(this, number);
        }
    }

    private void gotoWebsite(String url) {
        if(!TextUtils.isEmpty(url)) {
            IntentHelper.launchWeblink(this, url);
        }
    }

    private void gotoAddress(ListingsUiDetailModel data) {
        if(data.getAddress() != null && !TextUtils.isEmpty(data.getAddress().getStreet())
                && !TextUtils.isEmpty(data.getAddress().getCity())) {
            IntentHelper.launchMaps(this, data.getName(), data.getAddress().getStreet(), data.getAddress().getCity(), data.getAddress().getState());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap == null) {
            return;
        }

        this.googleMap = googleMap;

        LatLng userLoc = new LatLng(viewModel.getUserLocation().getLatitiude(), viewModel.getUserLocation().getLongitude());
        LatLng latLng = new LatLng(viewModel.getSelection().getValue().getLatitude(), viewModel.getSelection().getValue().getLongitude());

        LatLngBounds latLngBounds =  LatLngBounds.builder().include(userLoc).include(latLng).build();
        Log.d(TAG, latLngBounds.toString());

        drawCurrentLocationMarker(userLoc);
        drawListingMarker(latLng);

        googleMap.setOnMapLoadedCallback(() -> googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleMap = null;
    }

    private void drawListingMarker(LatLng latLng) {
       googleMap.addMarker(new MarkerOptions()
                .position(latLng));
    }

    private void drawCurrentLocationMarker(LatLng latLng) {
        googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.twotone_my_location_black_24))
                .position(latLng));
    }
}
