package bj.rxjavaexperimentation.search;

import android.content.Context;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import bj.rxjavaexperimentation.utils.AnalyticsTracker;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by Josh Laird on 02/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class SearchControllerUnitTest
{
    private SearchController controller;
    @Mock Context context;
    @Mock SearchContract.View view;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock AnalyticsTracker tracker;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new SearchController(context, view, imageViewAnimator, tracker);
        controller.requestModelBuild();
    }


}
