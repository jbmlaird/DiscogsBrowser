package bj.vinylbrowser.marketplace

import android.content.Context
import bj.vinylbrowser.model.listing.Listing
import bj.vinylbrowser.model.user.UserDetails
import bj.vinylbrowser.network.DiscogsInteractor
import bj.vinylbrowser.utils.schedulerprovider.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class MarketplacePresenterTest {
    lateinit var presenter: MarketplacePresenter
    val context: Context = mock()
    val view: MarketplaceContract.View  = mock()
    val discogsInteractor: DiscogsInteractor = mock()
    val epxController: MarketplaceEpxController = mock()
    lateinit var testScheduler: TestScheduler
    val listingId = "123"

    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        presenter = MarketplacePresenter(context, view, discogsInteractor, TestSchedulerProvider(testScheduler), epxController)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(view, discogsInteractor, epxController)
    }

    @Test
    fun getListingDetailsError_displaysError() {
        whenever(discogsInteractor.fetchListingDetails(listingId)).thenReturn(Single.error<Listing>(Throwable()))

        presenter.getListingDetails(listingId)
        testScheduler.triggerActions()

        verify(epxController, times(1)).setLoading(true)
        verify(discogsInteractor, times(1)).fetchListingDetails(listingId)
        verify(epxController, times(1)).setError(true)
    }

    @Test
    fun getListingDetailsValid_displayListingsAndUpdatesUser() {
        val listing = Listing()
        val userDetails = UserDetails()
        whenever(discogsInteractor.fetchListingDetails(listingId)).thenReturn(Single.just(listing))
        whenever(discogsInteractor.fetchUserDetails(listing.seller.username)).thenReturn(Single.just(userDetails))

        presenter.getListingDetails(listingId)
        testScheduler.triggerActions()

        verify(epxController, times(1)).setLoading(true)
        verify(discogsInteractor, times(1)).fetchListingDetails(listingId)
        verify(discogsInteractor, times(1)).fetchUserDetails(listing.seller.username)
        verify(epxController, times(1)).setListing(listing)
        verify(epxController, times(1)).setSellerDetails(userDetails)
    }

    @Test
    fun getListingDetailsError_displayListingsAndUpdatesUser() {
        val listing = Listing()
        val userDetails = UserDetails()
        whenever(discogsInteractor.fetchListingDetails(listingId)).thenReturn(Single.just(listing))
        whenever(discogsInteractor.fetchUserDetails(listing.seller.username)).thenReturn(Single.just(userDetails))

        presenter.getListingDetails(listingId)
        testScheduler.triggerActions()

        verify(epxController, times(1)).setLoading(true)
        verify(discogsInteractor, times(1)).fetchListingDetails(listingId)
        verify(discogsInteractor, times(1)).fetchUserDetails(listing.seller.username)
        verify(epxController, times(1)).setListing(listing)
        verify(epxController, times(1)).setSellerDetails(userDetails)
    }
}