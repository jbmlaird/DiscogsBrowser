package bj.discogsbrowser.artist;

import android.content.Context;

import com.airbnb.epoxy.EpoxyModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import bj.discogsbrowser.testmodels.TestArtistResult;
import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 04/05/2017.
 */

public class ArtistControllerUnitTest
{
    private ArtistController controller;
    @Mock Context context;
    @Mock ArtistContract.View view;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock AnalyticsTracker tracker;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new ArtistController(view, context, imageViewAnimator, tracker);
        controller.requestModelBuild();
    }

    @After
    public void tearDown()
    {
        // Not too fussed about the interactions, just want to make sure that the correct models build
//        verifyNoMoreInteractions(view, context, imageViewAnimator, tracker);
    }


    @Test
    public void initialLoadingState_correct()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 3);
    }

    @Test
    public void setArtistMembers_displaysArtistMembers()
    {
        controller.setArtist(new TestArtistResult());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 3);
    }
}
