package com.example.sauravrp.listings;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.sauravrp.listings.network.models.Contact;
import com.example.sauravrp.listings.network.models.ListingDetail;
import com.example.sauravrp.listings.network.models.Location;
import com.example.sauravrp.listings.repo.interfaces.IDataModel;
import com.example.sauravrp.listings.repo.interfaces.IStorageModel;
import com.example.sauravrp.listings.service.interfaces.ILocationService;
import com.example.sauravrp.listings.viewmodels.ListingDetailViewModel;
import com.example.sauravrp.listings.viewmodels.helper.ModelConverters;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiDetailModel;
import com.example.sauravrp.listings.viewmodels.models.ListingsUiModel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;

/**
 * This annotation tells the Mockito test runner to validate that your usage of the framework is correct and simplifies the initialization of your mock objects.
 */
@RunWith(MockitoJUnitRunner.class)

public class ListingsDetailViewModelTest {

    private ListingDetailViewModel listingDetailViewModel;

    @Mock
    private IDataModel dataModel;

    @Mock
    private ILocationService locationService;

    @Mock
    private IStorageModel storageModel;

    @Mock
    Observer<String> stringObserver;

    @Mock
    Observer<ListingsUiDetailModel> listingObserver;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        listingDetailViewModel = new ListingDetailViewModel(locationService, dataModel, storageModel);
    }


    @Test
    public void testPhoneNumberClick() {

        String phoneNumber = "111-111-1111";

        listingDetailViewModel.getSelectedPhoneNumber().observeForever(stringObserver);
        listingDetailViewModel.phoneNumberSelected(phoneNumber);
        verify(stringObserver).onChanged(phoneNumber);

    }

    @Test
    public void testWebsilteClick() {

        String website = "www.test.com";

        listingDetailViewModel.getSelectedWebSite().observeForever(stringObserver);
        listingDetailViewModel.websiteSelected(website);
        verify(stringObserver).onChanged(website);

    }

    @Test
    public void testAddressClick() {

        ListingsUiDetailModel data = createDetailsListingsUiModel("1");

        listingDetailViewModel.getSelectedAddress().observeForever(listingObserver);
        listingDetailViewModel.addressSelected(data);
        verify(listingObserver).onChanged(data);
    }

    @Test
    public void testSetSelection() {

        String id = "1";
        String name = "Delicious Pizza";


        ListingsUiModel uiModel = new ListingsUiModel(id, name);

        ListingDetail listing = new ListingDetail();
        listing.setId(id);
        listing.setName(name);
        listing.setLocation(new Location());
        Contact contact = new Contact();
        contact.setPhone("111-111-1111");
        listing.setContact(contact);

        ListingsUiDetailModel data = createDetailsListingsUiModel(listing);

        Mockito.when(locationService.distanceFromInMiles(data.getLatitude(), data.getLongitude())).thenReturn(1.0f);
        Mockito.when(dataModel.getListingDetail(id)).thenReturn(Single.just(listing));

        listingDetailViewModel.setSelection(uiModel);
        Assert.assertEquals(data, listingDetailViewModel.getSelection().getValue());
    }


    private ListingsUiDetailModel createDetailsListingsUiModel(String id) {
        ListingDetail listing = new ListingDetail();
        listing.setId(id);
        listing.setName("Delicious Pizza");
        listing.setLocation(new Location());
        Contact contact = new Contact();
        contact.setPhone("111-111-1111");
        listing.setContact(contact);

        return ModelConverters.createListingsUiDetailModel(listing, 2.0f);
    }

    private ListingsUiDetailModel createDetailsListingsUiModel(ListingDetail detail) {
        return ModelConverters.createListingsUiDetailModel(detail, 2.0f);

    }
}
