package bj.discogsbrowser.suite;

/**
 * Created by Josh Laird on 14/05/2017.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bj.discogsbrowser.label.LabelActivityMockPresenterTest;
import bj.discogsbrowser.login.LoginActivityTest;

// Runs all espresso tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({LabelActivityMockPresenterTest.class,
        LoginActivityTest.class})
public class EspressoSuite {}