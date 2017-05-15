package bj.discogsbrowser.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bj.discogsbrowser.artist.ArtistActivityTest;
import bj.discogsbrowser.label.LabelActivityMockPresenterTest;
import bj.discogsbrowser.login.LoginActivityTest;

/**
 * Created by Josh Laird on 14/05/2017.
 * <p>
 * Runs all espresso tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({LabelActivityMockPresenterTest.class,
        LoginActivityTest.class, ArtistActivityTest.class})
public class EspressoSuite {}