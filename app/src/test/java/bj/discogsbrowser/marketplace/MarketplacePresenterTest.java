package bj.discogsbrowser.marketplace;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.airbnb.epoxy.EpoxyControllerAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import bj.discogsbrowser.common.MyRecyclerView;
import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.user.UserDetails;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Josh Laird on 09/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MarketplacePresenterTest
{
    private MarketplacePresenter presenter;
    @Mock Context context;
    @Mock MarketplaceContract.View view;
    @Mock DiscogsInteractor discogsInteractor;
    @Mock MarketplaceController controller;
    private TestScheduler testScheduler;
    private String listingId = "123";
    private ListingFactory listingFactory = new ListingFactory();

    @Before
    public void setUp()
    {
        testScheduler = new TestScheduler();
        presenter = new MarketplacePresenter(context, view, discogsInteractor, new TestSchedulerProvider(testScheduler), controller);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(view, discogsInteractor, controller);
    }

    @Test
    public void getListingDetailsError_displaysError()
    {
        when(discogsInteractor.fetchListingDetails(listingId)).thenReturn(Single.error(new Throwable()));

        presenter.getListingDetails(listingId);
        testScheduler.triggerActions();

        verify(controller, times(1)).setLoading(true);
        verify(discogsInteractor, times(1)).fetchListingDetails(listingId);
        verify(controller, times(1)).setError(true);
    }

    @Test
    public void getListingDetailsValid_displayListingsAndUpdatesUser()
    {
        Listing listing = listingFactory.getListing();
        UserDetails userDetails = new UserDetails();
        when(discogsInteractor.fetchListingDetails(listingId)).thenReturn(Single.just(listing));
        when(discogsInteractor.fetchUserDetails(listing.getSeller().getUsername())).thenReturn(Single.just(userDetails));

        presenter.getListingDetails(listingId);
        testScheduler.triggerActions();

        verify(controller, times(1)).setLoading(true);
        verify(discogsInteractor, times(1)).fetchListingDetails(listingId);
        verify(discogsInteractor, times(1)).fetchUserDetails(listing.getSeller().getUsername());
        verify(controller, times(1)).setListing(listing);
        verify(controller, times(1)).setSellerDetails(userDetails);
    }

    @Test
    public void getListingDetailsError_displayListingsAndUpdatesUser()
    {
        Listing listing = listingFactory.getListing();
        UserDetails userDetails = new UserDetails();
        when(discogsInteractor.fetchListingDetails(listingId)).thenReturn(Single.just(listing));
        when(discogsInteractor.fetchUserDetails(listing.getSeller().getUsername())).thenReturn(Single.just(userDetails));

        presenter.getListingDetails(listingId);
        testScheduler.triggerActions();

        verify(controller, times(1)).setLoading(true);
        verify(discogsInteractor, times(1)).fetchListingDetails(listingId);
        verify(discogsInteractor, times(1)).fetchUserDetails(listing.getSeller().getUsername());
        verify(controller, times(1)).setListing(listing);
        verify(controller, times(1)).setSellerDetails(userDetails);
    }

    @Test
    public void setupRecyclerView_setsUpRecyclerView()
    {
        MyRecyclerView mockRv = mock(MyRecyclerView.class);
        EpoxyControllerAdapter mockAdapter = mock(EpoxyControllerAdapter.class);
        when(controller.getAdapter()).thenReturn(mockAdapter);

        presenter.setupRecyclerView(mockRv);

        verify(mockRv).setLayoutManager(any(LinearLayoutManager.class));
        verify(mockRv).setAdapter(mockAdapter);
        verify(controller).getAdapter();
        verifyNoMoreInteractions(mockRv);
    }
}
