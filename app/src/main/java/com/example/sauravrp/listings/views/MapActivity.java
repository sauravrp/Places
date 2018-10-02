package com.example.sauravrp.listings.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sauravrp.listings.R;
import com.example.sauravrp.listings.network.models.Listing;
import com.example.sauravrp.listings.viewmodels.ListingsViewModel;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final static String TAG = "MapActivity";
    private final static String QUERY = "query";
    private final static int ZOOM_LEVEL = 12;

    private GoogleMap googleMap;
    @Inject
    ListingsViewModel listingsViewModel;

    private GoogleMap.OnInfoWindowClickListener markerInfoOnClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            listingsViewModel.selectListing((ListingsUiModel)marker.getTag());
        }
    };

    public static void startActivity(Context ctx, String query) {
        Intent intent = new Intent(ctx, MapActivity.class);
        intent.putExtra(QUERY, query);
        ctx.startActivity(intent);
    }

    private CompositeDisposable compositeDisposable;

    private String getQueryStringFromBundle() {
        Bundle extras = getIntent().getExtras();
        return extras.getString(QUERY);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupGoogleMap();
        listingsViewModel.getSelectedListing().observe(this, this::listingSelected);
    }

    private void setupGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.details_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBind();
    }

    private void bind() {
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(listingsViewModel.getListings()
                .subscribe(this::showResults, this::showError));
    }

    private void unBind() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    private void showResults(List<ListingsUiModel> resultList) {
        if (resultList.size() == 0) {
            Toast.makeText(this, getString(R.string.results_not_found), Toast.LENGTH_LONG ).show();
            return;
        }

        if(googleMap != null) {
            LatLngBounds.Builder latLngBoundsBuilder =  LatLngBounds.builder();
            for(ListingsUiModel item: resultList) {
                LatLng latLng = new LatLng(item.getLatitude(), item.getLongitude());
                drawListingMarker(item, latLng);
                latLngBoundsBuilder.include(latLng);
            }

            LatLngBounds latLngBounds = latLngBoundsBuilder.build();
            googleMap.setOnMapLoadedCallback(() -> googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100)));
        }
    }

    private void showError(Throwable error) {
        Toast.makeText(this, getString(R.string.error_fetching_result), Toast.LENGTH_LONG ).show();
        Log.d(TAG, error.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnInfoWindowClickListener(markerInfoOnClickListener);
        listingsViewModel.searchListings(getQueryStringFromBundle());

    }

    private void drawListingMarker(ListingsUiModel listing, LatLng latLng) {
        Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng).title(listing.getName()));
        marker.setTag(listing);
    }

    private void listingSelected(ListingsUiModel selection) {
        ListingDetailActivity.startActivity(this, selection);
    }


}
