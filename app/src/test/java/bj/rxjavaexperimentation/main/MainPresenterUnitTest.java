package bj.rxjavaexperimentation.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import bj.rxjavaexperimentation.model.listing.Listing;
import bj.rxjavaexperimentation.model.order.Order;
import bj.rxjavaexperimentation.model.user.UserDetails;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.utils.AnalyticsTracker;
import bj.rxjavaexperimentation.utils.NavigationDrawerBuilder;
import bj.rxjavaexperimentation.utils.SharedPrefsManager;
import bj.rxjavaexperimentation.utils.schedulerprovider.TestSchedulerProvider;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Example local unit buildNavigationDrawer_succeeds, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterUnitTest
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
    @Mock AnalyticsTracker tracker;

    @Mock MainActivity mainActivity;
    @Mock Toolbar toolbar;
    @Mock Drawer drawer;

    @Before
    public void setUp()
    {
        testUserDetails = new UserDetails();
        testUserDetails.setUsername(username);
        testScheduler = new TestScheduler();
        mainPresenter = new MainPresenter(context, mView, discogsInteractor, new TestSchedulerProvider(testScheduler), navigationDrawerBuilder, mainController, sharedPrefsManager, logWrapper, tracker);
    }

    @After
    public void tearDown()
    {
        verifyNoMoreInteractions(mView, discogsInteractor, navigationDrawerBuilder, mainController, sharedPrefsManager, logWrapper);
    }

    @Test
    public void buildNavigationDrawer_succeeds()
    {
        List<Order> listOrders = new ArrayList();
        List<Listing> listSelling = new ArrayList();
        when(sharedPrefsManager.getUsername()).thenReturn(username);
        when(discogsInteractor.fetchUserDetails()).thenReturn(Single.just(testUserDetails));
        when(discogsInteractor.fetchOrders()).thenReturn(Single.just(listOrders));
        when(discogsInteractor.fetchSelling(username)).thenReturn(Single.just(listSelling));
        when(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar)).thenReturn(drawer);

        mainPresenter.connectAndBuildNavigationDrawer(mainActivity, toolbar);
        testScheduler.triggerActions();

        verify(sharedPrefsManager, times(1)).storeUserDetails(testUserDetails);
        verify(discogsInteractor, times(1)).fetchUserDetails();
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

        mainPresenter.connectAndBuildNavigationDrawer(mainActivity, toolbar);
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchUserDetails();
        verify(mainController).setOrdersError(true);
        verify(navigationDrawerBuilder, times(1)).buildNavigationDrawer(mainActivity, toolbar);
        verify(mView).setDrawer(drawer);
        verify(mView).setupRecyclerView();
        verify(logWrapper).e(any(String.class), any(String.class));
    }

    @Test
    public void setupRecyclerView_setsUpRecyclerView()
    {
        mainPresenter.setupRecyclerView(mainActivity, recyclerView);

        verify(mainController, times(1)).getAdapter();
        verify(mainController, times(1)).requestModelBuild();
    }

    @Test
    public void retrySuccessful_displaysInfo()
    {
        List<Order> listOrders = new ArrayList();
        List<Listing> listSelling = new ArrayList();
        when(sharedPrefsManager.getUsername()).thenReturn(username);
        when(discogsInteractor.fetchUserDetails()).thenReturn(Single.just(testUserDetails));
        when(discogsInteractor.fetchOrders()).thenReturn(Single.just(listOrders));
        when(discogsInteractor.fetchSelling(username)).thenReturn(Single.just(listSelling));

        mainPresenter.retry();
        testScheduler.triggerActions();

        verify(discogsInteractor, times(1)).fetchUserDetails();
        verify(discogsInteractor, times(1)).fetchOrders();
        verify(sharedPrefsManager, times(1)).getUsername();
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
}