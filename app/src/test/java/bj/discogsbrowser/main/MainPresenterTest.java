package bj.discogsbrowser.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.R;
import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.greendao.ViewedRelease;
import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.order.Order;
import bj.discogsbrowser.model.search.SearchResult;
import bj.discogsbrowser.model.user.UserDetails;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.testmodels.TestRootSearchResponse;
import bj.discogsbrowser.utils.NavigationDrawerBuilder;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.schedulerprovider.TestSchedulerProvider;
import bj.discogsbrowser.wrappers.LogWrapper;
import edu.emory.mathcs.backport.java.util.Collections;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest
{
    private String username = "BJLairy";
    private MainPresenter mainPresenter;

    private TestScheduler testScheduler;
    private UserDetails testUserDetails;
    @Mock Context context;
    @Mock MainContract.View mView;
    @Mock DiscogsInteractor discogsInteractor;
    @Mock NavigationDrawerBuilder navigationDrawerBuilder;
    @Mock MainController mainController;
    @Mock RecyclerView recyclerView;
    @Mock SharedPrefsManager sharedPrefsManager;
    @Mock LogWrapper logWrapper;
    @Mock DaoManager daoManager;
    @Mock AnalyticsTracker tracker;

    @Mock MainActivity mainActivity;
    @Mock Toolbar toolbar;
    @Mock Drawer drawer;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        testUserDetails = new UserDetails();
        testUserDetails.setUsername(username);
        testScheduler = new TestScheduler();
        mainPresenter = new MainPresenter(context, mView, discogsInteractor, new TestSchedulerProvider(testScheduler), navigationDrawerBuilder, mainController, sharedPrefsManager, logWrapper, daoManager, tracker);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(mView, discogsInteractor, navigationDrawerBuilder, mainController, sharedPrefsManager, logWrapper, daoManager, tracker);
    }

    @Test
    public void buildNavigationDrawer_succeeds()
    {
        List<Order> listOrders = new ArrayList();
        List<Listing> listSelling = new ArrayList();
        when(sharedPrefsManager.getUsername()).thenReturn(username);
        when(discogsInteractor.fetchUserDetails()).thenReturn(Single.just(testUserDetails));
        when(context.getString(R.string.main_activity)).thenReturn("MainActivity");
        when(context.getString(R.string.logged_in)).thenReturn("logged in");
        when(discogsInteractor.fetchOrders()).thenReturn(Single.just(listOrders));
        when(discogsInteractor.fetchSelling(username)).thenReturn(Single.just(listSelling));
        when(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar)).thenReturn(drawer);
        when(mView.getActivity()).thenReturn(mainActivity);

        mainPresenter.connectAndBuildNavigationDrawer(toolbar);
        testScheduler.triggerActions();

        verify(mView, times(1)).showLoading(true);
        verify(mView).getActivity();
        verify(sharedPrefsManager, times(1)).storeUserDetails(testUserDetails);
        verify(discogsInteractor, times(1)).fetchUserDetails();
        verify(tracker).send("MainActivity", "MainActivity", "logged in", testUserDetails.getUsername(), 1L);
        verify(discogsInteractor, times(1)).fetchOrders();
        verify(sharedPrefsManager, times(1)).getUsername();
        verify(mainController, times(1)).setOrders(listOrders);
        verify(discogsInteractor, times(1)).fetchSelling(username);
        verify(mainController, times(1)).setSelling(listSelling);
        verify(mView, times(1)).setDrawer(drawer);
        verify(navigationDrawerBuilder, times(1)).buildNavigationDrawer(mainActivity, toolbar);
        verify(mView, times(1)).setupRecyclerView();
        verify(mainController, times(1)).setLoadingMorePurchases(true);
    }

    @Test
    public void buildNavigationDrawerUserDetailsError_handles() throws UnknownHostException
    {
        when(discogsInteractor.fetchUserDetails()).thenReturn(Single.error(new UnknownHostException()));
        when(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar)).thenReturn(drawer);
        when(mView.getActivity()).thenReturn(mainActivity);

        mainPresenter.connectAndBuildNavigationDrawer(toolbar);
        testScheduler.triggerActions();

        verify(mView).getActivity();
        verify(mView, times(1)).showLoading(true);
        verify(discogsInteractor, times(1)).fetchUserDetails();
        verify(mainController).setOrdersError(true);
        verify(navigationDrawerBuilder, times(1)).buildNavigationDrawer(mainActivity, toolbar);
        verify(mView).setDrawer(drawer);
        verify(mView).setupRecyclerView();
        verify(logWrapper).e(any(String.class), any(String.class));
    }

//    @Test
//    public void setupRecyclerView_setsUpRecyclerView()
//    {
//        mainPresenter.setupRecyclerView(mainActivity, recyclerView);
//
//        verify(mainController, times(1)).getAdapter();
//        verify(mainController, times(1)).requestModelBuild();
//    }

    @Test
    public void retrySuccessful_displaysInfo()
    {
        List<Order> listOrders = new ArrayList();
        List<Listing> listSelling = new ArrayList();
        when(sharedPrefsManager.getUsername()).thenReturn(username);
        when(context.getString(R.string.main_activity)).thenReturn("MainActivity");
        when(context.getString(R.string.logged_in)).thenReturn("logged in");
        when(discogsInteractor.fetchUserDetails()).thenReturn(Single.just(testUserDetails));
        when(discogsInteractor.fetchOrders()).thenReturn(Single.just(listOrders));
        when(discogsInteractor.fetchSelling(username)).thenReturn(Single.just(listSelling));

        mainPresenter.retry();
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchUserDetails();
        verify(discogsInteractor, times(1)).fetchOrders();
        verify(sharedPrefsManager, times(1)).getUsername();
        verify(tracker).send("MainActivity", "MainActivity", "logged in", testUserDetails.getUsername(), 1L);
        verify(sharedPrefsManager, times(1)).storeUserDetails(testUserDetails);
        verify(mainController, times(1)).setLoadingMorePurchases(true);
        verify(mainController, times(1)).setOrders(listOrders);
        verify(discogsInteractor, times(1)).fetchSelling(username);
        verify(mainController, times(1)).setSelling(listSelling);
    }

    @Test
    public void retryError_displaysError() throws Exception
    {
        when(discogsInteractor.fetchUserDetails()).thenReturn(Single.error(new Exception()));

        mainPresenter.retry();
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchUserDetails();
        verify(mainController, times(1)).setOrdersError(true);
    }

    @Test
    public void buildRecommendationsEmptyList_ControllerEmptyList()
    {
        List list = Collections.emptyList();
        when(daoManager.getViewedReleases()).thenReturn(list);

        mainPresenter.buildRecommendations();

        verify(daoManager, times(1)).getViewedReleases();
        verify(mainController).setRecommendations(list);
    }

    @Test
    public void buildRecommendationsError_ControllerError()
    {
        ArrayList<ViewedRelease> viewedReleases = new ArrayList<>();
        viewedReleases.add(MainFactory.buildViewedRelease(4));
        when(daoManager.getViewedReleases()).thenReturn(viewedReleases);
        when(discogsInteractor.searchByStyle(viewedReleases.get(0).getStyle(), "1", false)).thenReturn(Single.error(new Throwable()));
        when(discogsInteractor.searchByLabel(viewedReleases.get(0).getLabelName())).thenReturn(Single.error(new Throwable()));

        mainPresenter.buildRecommendations();
        testScheduler.triggerActions();

        assertEquals(daoManager.getViewedReleases(), viewedReleases);
        verify(daoManager, times(2)).getViewedReleases();
        verify(discogsInteractor, times(1)).searchByStyle(viewedReleases.get(0).getStyle(), "1", false);
        verify(discogsInteractor, times(1)).searchByLabel(viewedReleases.get(0).getLabelName());
        verify(mainController, times(1)).setRecommendationsError(true);
    }

    @Test
    public void buildRecommendationsOver24_ControllerDisplaysTruncatedLists()
    {
        final ArgumentCaptor searchResultCaptor = ArgumentCaptor.forClass(List.class);
        ArrayList<ViewedRelease> viewedReleases = new ArrayList<>();
        viewedReleases.add(MainFactory.buildViewedRelease(1));
        when(daoManager.getViewedReleases()).thenReturn(viewedReleases);
        // TestSearchResponse contains 20 entries each
        when(discogsInteractor.searchByStyle(viewedReleases.get(0).getStyle(), "1", false)).thenReturn(Single.just(new TestRootSearchResponse()));
        when(discogsInteractor.searchByStyle(viewedReleases.get(0).getStyle(), String.valueOf(1), true)).thenReturn(Single.just(new TestRootSearchResponse()));
        when(discogsInteractor.searchByLabel(viewedReleases.get(0).getLabelName())).thenReturn(Single.just(new TestRootSearchResponse().getSearchResults()));

        mainPresenter.buildRecommendations();
        testScheduler.triggerActions();

        verify(daoManager, times(1)).getViewedReleases();
        verify(discogsInteractor, times(1)).searchByStyle(viewedReleases.get(0).getStyle(), "1", false);
        verify(discogsInteractor, times(1)).searchByStyle(viewedReleases.get(0).getStyle(), "1", true);
        verify(discogsInteractor, times(1)).searchByLabel(viewedReleases.get(0).getLabelName());
        verify(mainController, times(1)).setRecommendations((List<SearchResult>) searchResultCaptor.capture());
        // Truncated 40 to 24
        assertEquals(((List<SearchResult>) searchResultCaptor.getAllValues().get(0)).size(), 24);
    }
}