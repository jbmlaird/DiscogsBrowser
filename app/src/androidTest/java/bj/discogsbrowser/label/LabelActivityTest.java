package bj.discogsbrowser.label;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import bj.discogsbrowser.utils.AnalyticsTracker;

/**
 * Created by Josh Laird on 12/05/2017.
 */
@android.support.test.filters.LargeTest
@RunWith(AndroidJUnit4.class)
public class LabelActivityTest
{
    @Rule
    public ActivityTestRule<LabelActivity> mActivityTestRule = new ActivityTestRule<>(LabelActivity.class, false,
            // Don't launch the Activity immediately
            false);

    @Mock LabelPresenter presenter;
    @Mock AnalyticsTracker tracker;

    @Test
    public void test() throws InterruptedException
    {
        mActivityTestRule.launchActivity(null);
        Thread.sleep(1000);
    }
}
