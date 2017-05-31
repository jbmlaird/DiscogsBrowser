package bj.vinylbrowser.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bj.vinylbrowser.artist.ArtistControllerMockPresenterTest;
import bj.vinylbrowser.home.HomeControllerTest;
import bj.vinylbrowser.label.LabelControllerMockPresenterTest;
import bj.vinylbrowser.login.LoginActivityMockPresenterTest;
import bj.vinylbrowser.master.MasterControllerMockPresenterTest;
import bj.vinylbrowser.release.ReleaseControllerMockPresenterTest;
import bj.vinylbrowser.search.SearchControllerTest;
import bj.vinylbrowser.singlelist.SingleListControllerMockPresenterTest;

/**
 * Created by Josh Laird on 14/05/2017.
 * <p>
 * Runs all espresso tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ArtistControllerMockPresenterTest.class, HomeControllerTest.class, LabelControllerMockPresenterTest.class,
        LoginActivityMockPresenterTest.class, MasterControllerMockPresenterTest.class, ReleaseControllerMockPresenterTest.class,
        SearchControllerTest.class, SingleListControllerMockPresenterTest.class})
//@Suite.SuiteClasses({SingleListControllerMockPresenterTest.class})
//        , ArtistReleasesMockNetworkTest.class, LabelControllerMockPresenterTest.class,
//        LoginActivityMockPresenterTest.class, MainActivityTest.class, MarketplaceActivityMockPresenterTest.class,
//        MasterActivityMockPresenterTest.class, OrderActivityMockPresenterTest.class, ReleaseActivityMockPresenterTest.class,
//        SearchActivityTest.class, SingleListMockPresenterTest.class})
public class EspressoSuite {}