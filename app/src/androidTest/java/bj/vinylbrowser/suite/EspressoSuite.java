package bj.vinylbrowser.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bj.vinylbrowser.artist.ArtistControllerMockPresenterTest;
import bj.vinylbrowser.home.HomeControllerTest;

/**
 * Created by Josh Laird on 14/05/2017.
 * <p>
 * Runs all espresso tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ArtistControllerMockPresenterTest.class, HomeControllerTest.class})
//        , ArtistReleasesMockNetworkTest.class, LabelControllerMockPresenterTest.class,
//        LoginActivityMockPresenterTest.class, MainActivityTest.class, MarketplaceActivityMockPresenterTest.class,
//        MasterActivityMockPresenterTest.class, OrderActivityMockPresenterTest.class, ReleaseActivityMockPresenterTest.class,
//        SearchActivityTest.class, SingleListMockPresenterTest.class})
public class EspressoSuite {}