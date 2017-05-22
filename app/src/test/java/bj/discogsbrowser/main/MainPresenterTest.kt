package bj.discogsbrowser.main

import android.content.Context
import android.support.v7.widget.Toolbar
import bj.discogsbrowser.R
import bj.discogsbrowser.greendao.DaoManager
import bj.discogsbrowser.greendao.ViewedRelease
import bj.discogsbrowser.model.listing.Listing
import bj.discogsbrowser.model.order.Order
import bj.discogsbrowser.model.search.RootSearchResponse
import bj.discogsbrowser.model.search.SearchResult
import bj.discogsbrowser.model.user.UserDetails
import bj.discogsbrowser.network.DiscogsInteractor
import bj.discogsbrowser.search.RootSearchResponseFactory
import bj.discogsbrowser.utils.NavigationDrawerBuilder
import bj.discogsbrowser.utils.SharedPrefsManager
import bj.discogsbrowser.utils.analytics.AnalyticsTracker
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider
import bj.discogsbrowser.wrappers.LogWrapper
import com.mikepenz.materialdrawer.Drawer
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

/**
 * Created by Josh Laird on 21/05/2017.
 */
@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {
    val username = "BJLairy"
    lateinit var mainPresenter: MainPresenter

    var testScheduler: TestScheduler = mock()
    var testUserDetails: UserDetails = mock()
    var context: Context = mock()
    var mView: MainContract.View = mock()
    var discogsInteractor: DiscogsInteractor = mock()
    var navigationDrawerBuilder: NavigationDrawerBuilder = mock()
    var mainController: MainController = mock()
    var sharedPrefsManager: SharedPrefsManager = mock()
    var logWrapper: LogWrapper = mock()
    var daoManager: DaoManager = mock()
    var tracker: AnalyticsTracker = mock()

    var mainActivity: MainActivity = mock()
    var toolbar: Toolbar = mock()
    var drawer: Drawer = mock()

    @Before
    fun setUp() {
        testUserDetails = UserDetails()
        testUserDetails.username = username
        testScheduler = TestScheduler()
        mainPresenter = MainPresenter(context, mView, discogsInteractor, TestSchedulerProvider(testScheduler), navigationDrawerBuilder, mainController, sharedPrefsManager, logWrapper, daoManager, tracker)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mView, discogsInteractor, navigationDrawerBuilder, mainController, sharedPrefsManager, logWrapper, daoManager, tracker)
    }

    @Test
    fun buildNavigationDrawer_succeeds() {
        val listOrders = emptyList<Order>()
        val listSelling = emptyList<Listing>()
        whenever(sharedPrefsManager.username).thenReturn(username)
        whenever(discogsInteractor.fetchUserDetails()).thenReturn(Single.just(testUserDetails))
        whenever(context.getString(R.string.main_activity)).thenReturn("MainActivity")
        whenever(context.getString(R.string.logged_in)).thenReturn("logged in")
        whenever(discogsInteractor.fetchOrders()).thenReturn(Single.just<List<Order>>(listOrders))
        whenever(discogsInteractor.fetchSelling(username)).thenReturn(Single.just<List<Listing>>(listSelling))
        whenever(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar)).thenReturn(drawer)
        whenever(mView.activity).thenReturn(mainActivity)

        mainPresenter.connectAndBuildNavigationDrawer(toolbar)
        testScheduler.triggerActions()

        verify(mView, times(1)).showLoading(true)
        verify(mView).activity
        verify(sharedPrefsManager, times(1)).storeUserDetails(testUserDetails)
        verify(discogsInteractor, times(1)).fetchUserDetails()
        verify(tracker).send("MainActivity", "MainActivity", "logged in", testUserDetails.username, 1)
        verify(discogsInteractor, times(1)).fetchOrders()
        verify(sharedPrefsManager, times(1)).getUsername()
        verify(mainController, times(1)).setOrders(listOrders)
        verify(discogsInteractor, times(1)).fetchSelling(username)
        verify(mainController, times(1)).setSelling(listSelling)
        verify(mView, times(1)).setDrawer(drawer)
        verify(navigationDrawerBuilder, times(1)).buildNavigationDrawer(mainActivity, toolbar)
        verify(mView, times(1)).setupRecyclerView()
        verify(mainController, times(1)).setLoadingMorePurchases(true)
    }

    @Test
    @Throws(UnknownHostException::class)
    fun buildNavigationDrawerUserDetailsError_handles() {
        whenever(discogsInteractor.fetchUserDetails()).thenReturn(Single.error<UserDetails>(UnknownHostException()))
        whenever(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar)).thenReturn(drawer)
        whenever(mView.activity).thenReturn(mainActivity)

        mainPresenter.connectAndBuildNavigationDrawer(toolbar)
        testScheduler.triggerActions()

        verify(mView).activity
        verify(mView, times(1)).showLoading(true)
        verify(discogsInteractor, times(1)).fetchUserDetails()
        verify(mainController).setOrdersError(true)
        verify(navigationDrawerBuilder, times(1)).buildNavigationDrawer(mainActivity, toolbar)
        verify(mView).setDrawer(drawer)
        verify(mView).setupRecyclerView()
        verify(logWrapper).e(any(String::class.java), any(String::class.java))
    }

    @Test
    fun retrySuccessful_displaysInfo() {
        val listOrders = emptyList<Order>()
        val listSelling = emptyList<Listing>()
        whenever(sharedPrefsManager.username).thenReturn(username)
        whenever(context.getString(R.string.main_activity)).thenReturn("MainActivity")
        whenever(context.getString(R.string.logged_in)).thenReturn("logged in")
        whenever(discogsInteractor.fetchUserDetails()).thenReturn(Single.just(testUserDetails))
        whenever(discogsInteractor.fetchOrders()).thenReturn(Single.just<List<Order>>(listOrders))
        whenever(discogsInteractor.fetchSelling(username)).thenReturn(Single.just<List<Listing>>(listSelling))

        mainPresenter.retry()
        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchUserDetails()
        verify(discogsInteractor, times(1)).fetchOrders()
        verify(sharedPrefsManager, times(1)).getUsername()
        verify(tracker).send("MainActivity", "MainActivity", "logged in", testUserDetails.username, 1)
        verify(sharedPrefsManager, times(1)).storeUserDetails(testUserDetails)
        verify(mainController, times(1)).setLoadingMorePurchases(true)
        verify(mainController, times(1)).setOrders(listOrders)
        verify(discogsInteractor, times(1)).fetchSelling(username)
        verify(mainController, times(1)).setSelling(listSelling)
    }

    @Test
    @Throws(Exception::class)
    fun retryError_displaysError() {
        whenever(discogsInteractor.fetchUserDetails()).thenReturn(Single.error<UserDetails>(Exception()))

        mainPresenter.retry()
        testScheduler.triggerActions()

        verify(discogsInteractor, times(1)).fetchUserDetails()
        verify(mainController, times(1)).setOrdersError(true)
    }

    @Test
    fun buildRecommendationsEmptyList_ControllerEmptyList() {
        val list = mutableListOf<ViewedRelease>()
        whenever(daoManager.viewedReleases).thenReturn(list)

        mainPresenter.buildRecommendations()

        verify(daoManager, times(1)).viewedReleases
        verify(mainController).setRecommendations(emptyList())
    }

    @Test
    fun buildRecommendationsError_ControllerError() {
        val viewedReleases = ViewedReleaseFactory.buildViewedReleases(4)
        whenever(daoManager.viewedReleases).thenReturn(viewedReleases)
        whenever(discogsInteractor.searchByStyle(viewedReleases[0].style, "1", false)).thenReturn(Single.error<RootSearchResponse>(Throwable()))
        whenever(discogsInteractor.searchByLabel(viewedReleases[0].labelName)).thenReturn(Single.error<List<SearchResult>>(Throwable()))

        mainPresenter.buildRecommendations()
        testScheduler.triggerActions()

        assertEquals(daoManager.viewedReleases, viewedReleases)
        verify(daoManager, times(2)).viewedReleases
        verify(discogsInteractor, times(1)).searchByStyle(viewedReleases[0].style, "1", false)
        verify(discogsInteractor, times(1)).searchByLabel(viewedReleases[0].labelName)
        verify(mainController, times(1)).setRecommendationsError(true)
    }

    @Test
    fun buildRecommendationsOver24_ControllerDisplaysTruncatedLists() {
        val searchResultCaptor = ArgumentCaptor.forClass(MutableList::class.java)
        val viewedReleases = ViewedReleaseFactory.buildViewedReleases(1)
        val rootSearchResponse = RootSearchResponseFactory.buildRootSearchResponse()
        whenever(daoManager.viewedReleases).thenReturn(viewedReleases)
        // TestSearchResponse contains 20 entries each
        whenever(discogsInteractor.searchByStyle(viewedReleases[0].style, "1", false)).thenReturn(Single.just(rootSearchResponse))
        whenever(discogsInteractor.searchByStyle(viewedReleases[0].style, 1.toString(), true)).thenReturn(Single.just(rootSearchResponse))
        whenever(discogsInteractor.searchByLabel(viewedReleases[0].labelName)).thenReturn(Single.just(rootSearchResponse.searchResults))

        mainPresenter.buildRecommendations()
        testScheduler.triggerActions()

        verify(daoManager, times(1)).viewedReleases
        verify(discogsInteractor, times(1)).searchByStyle(viewedReleases[0].style, "1", false)
        verify(discogsInteractor, times(1)).searchByStyle(viewedReleases[0].style, "1", true)
        verify(discogsInteractor, times(1)).searchByLabel(viewedReleases[0].labelName)
        verify(mainController, times(1)).setRecommendations(capture(searchResultCaptor) as MutableList<SearchResult>?)
        // Truncated 40 to 24
        assertEquals((searchResultCaptor.allValues[0] as MutableList<SearchResult>).size, 24)
    }
}