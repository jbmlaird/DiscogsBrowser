package bj.discogsbrowser.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bj.discogsbrowser.artist.ArtistActivityMockPresenterTest;
import bj.discogsbrowser.label.LabelActivityMockPresenterTest;
import bj.discogsbrowser.login.LoginActivityMockPresenterTest;

/**
 * Created by Josh Laird on 14/05/2017.
 * <p>
 * Runs all espresso tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({LabelActivityMockPresenterTest.class,
        LoginActivityMockPresenterTest.class, ArtistActivityMockPresenterTest.class})
public class EspressoSuite {}