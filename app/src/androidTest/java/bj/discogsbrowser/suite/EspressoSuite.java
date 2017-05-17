package bj.discogsbrowser.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bj.discogsbrowser.artist.ArtistActivityMockPresenterTest;
import bj.discogsbrowser.artistreleases.ArtistReleasesMockNetworkTest;
import bj.discogsbrowser.label.LabelActivityMockPresenterTest;
import bj.discogsbrowser.login.LoginActivityMockPresenterTest;
import bj.discogsbrowser.main.MainActivityTest;
import bj.discogsbrowser.marketplace.MarketplaceActivityMockPresenterTest;
import bj.discogsbrowser.master.MasterActivityMockPresenterTest;
import bj.discogsbrowser.order.OrderActivityMockPresenterTest;
import bj.discogsbrowser.release.ReleaseActivityMockPresenterTest;
import bj.discogsbrowser.search.SearchActivityTest;
import bj.discogsbrowser.singlelist.SingleListActivity;

/**
 * Created by Josh Laird on 14/05/2017.
 * <p>
 * Runs all espresso tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ArtistActivityMockPresenterTest.class, ArtistReleasesMockNetworkTest.class, LabelActivityMockPresenterTest.class,
        LoginActivityMockPresenterTest.class, MainActivityTest.class, MarketplaceActivityMockPresenterTest.class,
        MasterActivityMockPresenterTest.class, OrderActivityMockPresenterTest.class, ReleaseActivityMockPresenterTest.class,
        SearchActivityTest.class, SingleListActivity.class})
public class EspressoSuite {}