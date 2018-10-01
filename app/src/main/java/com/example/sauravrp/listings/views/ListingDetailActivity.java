package com.example.sauravrp.listings.views;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.example.sauravrp.listings.BR;
import com.example.sauravrp.listings.R;
import com.example.sauravrp.listings.helpers.IntentHelper;
import com.example.sauravrp.listings.viewmodels.ListingDetailViewModel;
import com.example.sauravrp.listings.views.models.ListingsUiModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class ListingDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final static String SELECTION = "selection";

    public static void startActivity(Context ctx, ListingsUiModel selected) {
        Intent intent = new Intent(ctx, ListingDetailActivity.class);
        intent.putExtra(SELECTION, selected);
        ctx.startActivity(intent);
    }

    @Inject
    ListingDetailViewModel viewModel;

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_listing_detail);
        binding.setLifecycleOwner(this);

        ButterKnife.bind(this);

        viewModel.setSelection(getSelectionFromBundle());
        binding.setVariable(BR.viewModel, viewModel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel.getSelectedAddress().observe(this, this::gotoAddress);
        viewModel.getSelectedPhoneNumber().observe(this, this::callPhoneNumber);

        setupGoogleMap();
    }

    private void setupGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.details_map);
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

    private void gotoAddress(ListingsUiModel data) {
//        if(!TextUtils.isEmpty(data.getAddress().getStreet())
//                && !TextUtils.isEmpty(data.getAddress().getCity())) {
//            IntentHelper.launchMaps(this, data.getTitle(), data.getAddress().getStreet(), data.getAddress().getCity(), data.getAddress().getState());
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng seattle = new LatLng(viewModel.getUserLocation().getLatitiude(),
                viewModel.getUserLocation().getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(seattle));
    }
}
