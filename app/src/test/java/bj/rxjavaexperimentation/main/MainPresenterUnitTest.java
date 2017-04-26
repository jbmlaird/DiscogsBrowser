package bj.rxjavaexperimentation.main;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import bj.rxjavaexperimentation.main.epoxy.MainController;
import bj.rxjavaexperimentation.model.user.UserDetails;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.TestSchedulerProvider;
import bj.rxjavaexperimentation.utils.NavigationDrawerBuilder;
import bj.rxjavaexperimentation.utils.SharedPrefsManager;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit buildNavigationDrawer_initialisesActivity, which will execute on the development machine (host).
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
    @Mock MainContract.View mView;
    @Mock DiscogsInteractor discogsInteractor;
    @Mock NavigationDrawerBuilder navigationDrawerBuilder;
    @Mock MainController mainController;
    @Mock RecyclerView recyclerView;
    @Mock SharedPrefsManager sharedPrefsManager;
    @Mock LogWrapper logWrapper;

    @Mock MainActivity mainActivity;
    @Mock Toolbar toolbar;
    @Mock Drawer drawer;

    @Before
    public void setUp()
    {
        testUserDetails = new UserDetails();
        testUserDetails.setUsername(username);
        testScheduler = new TestScheduler();
        mainPresenter = new MainPresenter(mView, discogsInteractor, new TestSchedulerProvider(testScheduler), navigationDrawerBuilder, mainController, sharedPrefsManager, logWrapper);
    }

    @Test
    public void buildNavigationDrawer_initialisesActivity()
    {
        when(discogsInteractor.fetchUserDetails()).thenReturn(Observable.just(testUserDetails));
        when(discogsInteractor.fetchOrders()).thenReturn(Observable.just(Collections.emptyList()));
        when(discogsInteractor.fetchListings(username)).thenReturn(Observable.just(Collections.emptyList()));
        when(navigationDrawerBuilder.buildNavigationDrawer(mainActivity, toolbar, testUserDetails)).thenReturn(drawer);

        mainPresenter.buildNavigationDrawer(mainActivity, toolbar);
        testScheduler.triggerActions();

        verify(discogsInteractor).fetchUserDetails();
        verify(discogsInteractor).fetchOrders();
        verify(discogsInteractor).fetchListings(username);
        verify(mView).setDrawer(drawer);
        verify(navigationDrawerBuilder).buildNavigationDrawer(mainActivity, toolbar, testUserDetails);
        verify(mView).setupRecyclerView();
    }
}