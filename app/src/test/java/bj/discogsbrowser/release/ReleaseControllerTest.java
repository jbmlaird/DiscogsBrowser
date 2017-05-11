package bj.discogsbrowser.release;

import android.content.Context;

import com.airbnb.epoxy.EpoxyModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 10/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class ReleaseControllerTest
{
    private ReleaseController controller;
    @Mock Context context;
    @Mock ReleaseContract.View view;
    @Mock ArtistsBeautifier artistsBeautifer;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock CollectionWantlistPresenter presenter;
    @Mock AnalyticsTracker tracker;
    private ReleaseFactory releaseFactory = new ReleaseFactory();

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new ReleaseController(context, view, artistsBeautifer, imageViewAnimator, presenter, tracker);
        controller.requestModelBuild();
    }

    @Test
    public void initialLoadingState_correct()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 2);
    }
}
