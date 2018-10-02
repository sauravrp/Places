package com.example.sauravrp.listings.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.sauravrp.listings.R;
import com.example.sauravrp.listings.viewmodels.ListingsViewModel;
import com.example.sauravrp.listings.views.adapters.ListingsAdapter;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiModel;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class ListingsActivity extends AppCompatActivity {

    private final static String TAG = "ListingsActivity";
    private final static int MINIMUM_SEARCH_THRESHOLD_IN_MILLISECONDS = 400;

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

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.results_view)
    View resultsView;

    @BindView(R.id.results_not_found_text)
    TextView resultsNotFoundTextView;

    @BindView(R.id.welcome_intro_expandible_layout)
    ExpandableLinearLayout expandableLayout;

    @BindView(R.id.fab)
    FloatingActionButton fabButton;

    @OnClick(R.id.fab)
    void fabClicked(View v) {
        MapActivity.startActivity(this, searchQuery);
    }

    private String searchQuery;

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
        initSearchView();
    }

    private void initSearchView() {

        searchQuery = "";

        searchView.setQueryHint(getString(R.string.action_search));

        RxSearchView.queryTextChangeEvents(searchView)
                .skip(1)
                .debounce(MINIMUM_SEARCH_THRESHOLD_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {

                    String query = event.queryText().toString();


                    if (TextUtils.isEmpty(query)) {
                        showWelcomeView();

                        searchQuery = "";
                        placesList.clear();
                        listingsAdapter.notifyDataSetChanged();
                    } else {
                        expandableLayout.collapse();

                        if (!searchQuery.equals(query)) {

                            placesList.clear();
                            listingsAdapter.notifyDataSetChanged();
                            searchQuery = query;

                            showNetworkProgress();
                            listingsViewModel.searchListings(searchQuery);
                        }
                    }
                }, e -> Log.e(TAG, e.toString()));

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

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listingsAdapter = new ListingsAdapter(listingsViewModel, placesList);
        placesListView.setLayoutManager(layoutManager);
        placesListView.setAdapter(listingsAdapter);
    }

    private void showResults(List<ListingsUiModel> resultList) {
        if (resultList.size() == 0) {
            showResultsNotFoundView();
            return;
        }
        placesList.addAll(resultList);
        listingsAdapter.notifyDataSetChanged();
        showResultsView();
    }

    private void showError(Throwable error) {
        Log.d(TAG, error.toString());
        showErrorView();
    }

    private void showErrorView() {
        errorText.setVisibility(View.VISIBLE);

        progressView.setVisibility(View.GONE);
        resultsView.setVisibility(View.GONE);
        expandableLayout.collapse();
        resultsNotFoundTextView.setVisibility(View.GONE);
        fabButton.setVisibility(View.GONE);
    }

    private void showNetworkProgress() {
        progressView.setVisibility(View.VISIBLE);
        progressTextView.setText(R.string.fetching_data);

        errorText.setVisibility(View.GONE);
        resultsView.setVisibility(View.GONE);
        expandableLayout.collapse();
        resultsNotFoundTextView.setVisibility(View.GONE);
        fabButton.setVisibility(View.GONE);

    }

    private void showResultsView() {
        resultsView.setVisibility(View.VISIBLE);
        fabButton.setVisibility(View.VISIBLE);

        errorText.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);
        expandableLayout.collapse();
        resultsNotFoundTextView.setVisibility(View.GONE);
    }

    private void showWelcomeView() {

        expandableLayout.expand();

        resultsView.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);
        resultsNotFoundTextView.setVisibility(View.GONE);
        fabButton.setVisibility(View.GONE);
    }

    private void showResultsNotFoundView() {
        resultsNotFoundTextView.setVisibility(View.VISIBLE);

        expandableLayout.collapse();
        resultsView.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);
        fabButton.setVisibility(View.GONE);
    }


    private void resultSelected(ListingsUiModel selection) {
        ListingDetailActivity.startActivity(this, selection);
    }

}
