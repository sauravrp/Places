package com.example.sauravrp.listings;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.example.sauravrp.listings.network.models.unused.Listing;
import com.example.sauravrp.listings.viewmodels.ListingDetailViewModel;
import com.example.sauravrp.listings.views.models.ListingsUiModel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * This annotation tells the Mockito test runner to validate that your usage of the framework is correct and simplifies the initialization of your mock objects.
 */
@RunWith(MockitoJUnitRunner.class)

public class ListingsDetailViewModelTest {

    private ListingDetailViewModel listingDetailViewModel;

    @Mock
    Observer<String> stringObserver;

    @Mock
    Observer<ListingsUiModel> listingObserver;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setup() {

        listingDetailViewModel = new ListingDetailViewModel();
    }


    @Test
    public void testPhoneNumberClick() {

        String phoneNumber = "111-111-1111";

        listingDetailViewModel.getSelectedPhoneNumber().observeForever(stringObserver);
        listingDetailViewModel.phoneNumberSelected(phoneNumber);
        verify(stringObserver).onChanged(phoneNumber);

    }

    @Test
    public void testAddressClick() {

        ListingsUiModel data = createListingsUiModel("1");

        listingDetailViewModel.getSelectedAddress().observeForever(listingObserver);
        listingDetailViewModel.addressSelected(data);
        verify(listingObserver).onChanged(data);
    }

    @Test
    public void testSetSelection() {
        ListingsUiModel data = createListingsUiModel("1");

        listingDetailViewModel.setSelection(data);
        Assert.assertEquals(data, listingDetailViewModel.getSelection());
    }


    private ListingsUiModel createListingsUiModel(String id) {
        Listing listing = new Listing();
        listing.setId(id);
        listing.setTitle("Delicious Pizza");
        listing.setAddress("123 MLK blvd");
        listing.setCity("Austin");
        listing.setState("TX");
        listing.setPhone("111-111-1111");
        listing.setDistance(".7");

        return new ListingsUiModel(listing.getId(),
                listing.getTitle(), listing.getAddress(), listing.getCity(),
                listing.getState(), listing.getPhone(), listing.getDistance());
    }
}
