package bj.discogsbrowser.main;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Josh Laird on 12/05/2017.
 */
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest
{
    private MainActivity mainActivity;

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp()
    {
        mainActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void wtf() throws InterruptedException
    {
        Thread.sleep(10000);
    }
}
