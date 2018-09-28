package com.example.sauravrp.listings.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sauravrp.listings.R;
import com.example.sauravrp.listings.viewmodels.ListingsViewModel;
import com.example.sauravrp.listings.views.adapters.ListingsAdapter;
import com.example.sauravrp.listings.views.models.ListingsUiModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class ListingsActivity extends AppCompatActivity {

    private final static String TAG = "ListingsActivity";

    @Inject
    ListingsViewModel listingsViewModel;

    @BindView(R.id.list_view)
    RecyclerView placesListView;

    @BindView(R.id.error_text)
    TextView errorText;

    @BindView(R.id.progress_view)
    View progressView;

    @BindView(R.id.loading_text)
    TextView progressTextView;

    private CompositeDisposable compositeDisposable;

    private ListingsAdapter listingsAdapter;
    private final ArrayList<ListingsUiModel> placesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupRecyclerView();

        listingsViewModel.getSelectedListing().observe(this, this::resultSelected);

        showErrorText(false);
        showPlacesListView(false);
//        showLocationProgress(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        listingsViewModel.getListings().observe(this, location -> {
//            // this won't fire, until onStart is complete, location listener is lifecycle aware
//
//
//            listingsViewModel.searchListings(Location.createLocation(location), 0);
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        bind();

        showErrorText(false);
//        showLocationProgress(false);
        showNetworkProgress(true);
        listingsViewModel.searchListings("pizza");


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
        if(compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        listingsAdapter = new ListingsAdapter(listingsViewModel, placesList);

//        EndlessRecyclerViewScrollListener endlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//
//                listingsViewModel.searchListings(totalItemsCount);
//            }
//        };
        placesListView.setLayoutManager(layoutManager);
        placesListView.addItemDecoration(itemDecoration);
//        placesListView.addOnScrollListener(endlessScrollListener);
        placesListView.setAdapter(listingsAdapter);

    }

    private void showResults(List<ListingsUiModel> resultList) {
        if(resultList.size() == 0)
            return;
        placesList.addAll(resultList);
        listingsAdapter.notifyDataSetChanged();
        showNetworkProgress(false);
//        showLocationProgress(false);
        showErrorText(false);
        showPlacesListView(true);
    }

    private void showError(Throwable error) {
        Log.d(TAG, error.toString());
        showNetworkProgress(false);
//        showLocationProgress(false);
        showErrorText(true);
        showPlacesListView(false);
    }

    private void showErrorText(boolean visible) {
        errorText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void showNetworkProgress(boolean visible) {
        progressView.setVisibility(visible ? View.VISIBLE : View.GONE);
        progressTextView.setText(R.string.fetching_data);
    }

//    private void showLocationProgress(boolean visible) {
//        progressView.setVisibility(visible ? View.VISIBLE : View.GONE);
//        progressTextView.setText(R.string.fetching_location);
//    }

    private void showPlacesListView(boolean visible) {
        placesListView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void resultSelected(ListingsUiModel selection) {
        ListingDetailActivity.startActivity(this, selection);
    }

}
