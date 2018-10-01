package com.example.sauravrp.listings;


import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.sauravrp.listings.network.models.unused.Listing;
import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.viewmodels.ListingsViewModel;
import com.example.sauravrp.listings.views.models.ListingsUiModel;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ListingsViewModelTest {

    @Mock
    private IDataModel dataModel;

    @Mock
    Observer<ListingsUiModel> listingObserver;

    private ListingsViewModel listingsViewModel;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    @BeforeClass
    public static void before() {
        /**
         * https://stackoverflow.com/questions/46549405/testing-asynchronous-rxjava-code-android
         */
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @AfterClass
    public static void after() {
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        listingsViewModel = new ListingsViewModel(dataModel);
    }


    @Test
    public void testListingSelected() {

        ListingsUiModel data = createListingsUiModel(createListing("1"));

        listingsViewModel.getSelectedListing().observeForever(listingObserver);
        listingsViewModel.selectListing(data);
        verify(listingObserver).onChanged(data);
    }


    @Test
    public void testGetListings() {
        ArrayList<Listing> serverResults = new ArrayList<>();
        serverResults.add(createListing("1"));
        serverResults.add(createListing("2"));

        ArrayList<ListingsUiModel> resultsUiModel = new ArrayList();
        resultsUiModel.add(createListingsUiModel(serverResults.get(0)));
        resultsUiModel.add(createListingsUiModel(serverResults.get(1)));

        Location location = new Location(0, 0);

        Mockito.when(dataModel.getListings(location.getLatitude(),
                location.getLongitude(),
                0)).thenReturn(Single.just(serverResults));


        TestObserver<List<ListingsUiModel>> testObserver = listingsViewModel.getListings()
                .test();

        testObserver.assertEmpty();

        listingsViewModel.searchListings(location, 0);

        testObserver.assertNoErrors();

        testObserver.assertValueCount(1);

        testObserver.assertValueAt(0, resultsUiModel);

    }


    @Test
    public void testGetListingWithJustOffset() {

        ArrayList<Listing> serverResults = new ArrayList<>();
        serverResults.add(createListing("1"));
        serverResults.add(createListing("2"));

        ArrayList<ListingsUiModel> resultsUiModel = new ArrayList();
        resultsUiModel.add(createListingsUiModel(serverResults.get(0)));
        resultsUiModel.add(createListingsUiModel(serverResults.get(1)));


        ArrayList<Listing> serverResultsSecondSet = new ArrayList<>();
        serverResultsSecondSet.add(createListing("3"));
        serverResultsSecondSet.add(createListing("4"));

        ArrayList<ListingsUiModel> resultsUiModelSecondSet = new ArrayList();
        resultsUiModelSecondSet.add(createListingsUiModel(serverResultsSecondSet.get(0)));
        resultsUiModelSecondSet.add(createListingsUiModel(serverResultsSecondSet.get(1)));

        Location location = new Location(0, 0);

        Mockito.when(dataModel.getListings(location.getLatitude(),
                location.getLongitude(),
                0)).thenReturn(Single.just(serverResults));

        Mockito.when(dataModel.getListings(location.getLatitude(),
                location.getLongitude(),
                1)).thenReturn(Single.just(serverResultsSecondSet));


        TestObserver<List<ListingsUiModel>> testObserver = listingsViewModel.getListings()
                .test();

        testObserver.assertEmpty();


        listingsViewModel.searchListings(location, 0);
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertValueAt(0, resultsUiModel);

        listingsViewModel.searchListings(1);
        testObserver.assertNoErrors();
        testObserver.assertValueCount(2);
        testObserver.assertValueAt(1, resultsUiModelSecondSet);
    }

    @Test
    public void testGetListingEmptyLocation() {
        TestObserver<List<ListingsUiModel>> testObserver = listingsViewModel.getListings()
                .test();

        testObserver.assertEmpty();

        listingsViewModel.searchListings(null, 0);
        testObserver.assertEmpty();
    }

    private Listing createListing(String id) {
        Listing listing = new Listing();
        listing.setId(id);
        listing.setName("Delicious Pizza");
        listing.setAddress("123 MLK blvd");
        listing.setCity("Austin");
        listing.setState("TX");
        listing.setPhone("111-111-1111");
        listing.setDistance(".7");

        return listing;
    }

    private ListingsUiModel createListingsUiModel(Listing listing) {
        return new ListingsUiModel(listing.getId(),
                listing.getName(), listing.getAddress(), listing.getCity(),
                listing.getState(), listing.getPhone(), listing.getDistance());
    }
}
