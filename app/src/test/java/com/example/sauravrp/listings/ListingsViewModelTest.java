package com.example.sauravrp.listings;


import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.sauravrp.listings.network.models.Contact;
import com.example.sauravrp.listings.network.models.Listing;
import com.example.sauravrp.listings.network.models.ListingDetail;
import com.example.sauravrp.listings.network.models.Location;
import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.repo.interfaces.IStorageModel;
import com.example.sauravrp.listings.service.interfaces.ILocationService;
import com.example.sauravrp.listings.viewmodels.ListingsViewModel;
import com.example.sauravrp.listings.viewmodels.helper.ModelConverters;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiDetailModel;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiModel;

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
    Observer<ListingsUiModel> listingObserver;

    @Mock
    private IDataModel dataModel;

    @Mock
    private ILocationService locationService;

    @Mock
    private IStorageModel storageModel;

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
        listingsViewModel = new ListingsViewModel(locationService, dataModel, storageModel);
    }


    @Test
    public void testListingSelected() {

        ListingsUiModel data = createListingsUiModel("1");

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


        String city = "Austin, Tx";
        com.example.sauravrp.listings.service.models.Location location = new com.example.sauravrp.listings.service.models.Location(city, 0.0, 0.0);
        Mockito.when(locationService.getUserLocation()).thenReturn(location);
        Mockito.when(locationService.distanceFromInMiles(0, 0)).thenReturn(2.0f);
        Mockito.when(dataModel.getListings(city, "pizza")).thenReturn(Single.just(serverResults));


        TestObserver<List<ListingsUiModel>> testObserver = listingsViewModel.getListings()
                .test();

        testObserver.assertEmpty();

        listingsViewModel.searchListings("pizza");

        testObserver.assertNoErrors();

        testObserver.assertValueCount(1);

        testObserver.assertValueAt(0, resultsUiModel);

    }

    private Listing createListing(String id) {

        String name = "Delicious Pizza";

        ListingDetail listing = new ListingDetail();
        listing.setId(id);
        listing.setName(name);
        listing.setLocation(new Location());
        Contact contact = new Contact();
        contact.setPhone("111-111-1111");
        listing.setContact(contact);
        return listing;
    }

    private ListingDetail createListingDetail(String id) {
        return (ListingDetail)createListing(id);
    }

    private ListingsUiModel createListingsUiModel(String id) {
        return ModelConverters.createListingsUiModel(createListing(id), 2.0f);
    }

    private ListingsUiModel createListingsUiModel(Listing listing) {
        return ModelConverters.createListingsUiModel(listing, 2.0f);
    }

    private ListingsUiDetailModel createDetailsListingsUiModel(String id) {
        return ModelConverters.createListingsUiDetailModel(createListingDetail(id), 2.0f);
    }
}
