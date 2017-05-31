package bj.vinylbrowser.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bj.vinylbrowser.artist.ArtistControllerMockPresenterTest;
import bj.vinylbrowser.artistreleases.ArtistReleasesControllerMockNetworkTest;
import bj.vinylbrowser.home.HomeControllerTest;
import bj.vinylbrowser.label.LabelControllerMockPresenterTest;
import bj.vinylbrowser.login.LoginActivityMockPresenterTest;
import bj.vinylbrowser.master.MasterControllerMockPresenterTest;
import bj.vinylbrowser.release.ReleaseControllerMockPresenterTest;
import bj.vinylbrowser.search.SearchControllerTest;
import bj.vinylbrowser.singlelist.SingleListControllerMockPresenterTest;
import bj.vinylbrowser.utils.GreendaoTest;

/**
 * Created by Josh Laird on 14/05/2017.
 * <p>
 * Runs all espresso tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ArtistControllerMockPresenterTest.class, ArtistReleasesControllerMockNetworkTest.class, HomeControllerTest.class, LabelControllerMockPresenterTest.class,
        LoginActivityMockPresenterTest.class, MasterControllerMockPresenterTest.class, ReleaseControllerMockPresenterTest.class,
        SearchControllerTest.class, SingleListControllerMockPresenterTest.class, GreendaoTest.class})
public class EspressoSuite {}