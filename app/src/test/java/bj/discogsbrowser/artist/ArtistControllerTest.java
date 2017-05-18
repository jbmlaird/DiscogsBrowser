package bj.discogsbrowser.artist;

import android.content.Context;

import com.airbnb.epoxy.EpoxyModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import bj.discogsbrowser.artistreleases.ArtistReleasesFactory;
import bj.discogsbrowser.artistreleases.ArtistResultFactory;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 04/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class ArtistControllerTest
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
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 4);
    }

    @Test
    public void setArtistNoMembers_displaysNoMembers()
    {
        controller.setArtist(ArtistReleasesFactory.getTestArtistResultNoMembers());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "ViewReleasesModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "UrlModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "UrlModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 8);
    }

    @Test
    public void setArtistTwoMembers_displaysTwoMembers()
    {
        controller.setArtist(ArtistResultFactory.buildArtistResult(2));
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "MemberModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "MemberModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "ViewReleasesModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "UrlModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "UrlModel_");
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 12);
    }

    @Test
    public void setArtistWithWantedUrl_displaysArtistWithWantedUrl()
    {
        controller.setArtist(ArtistReleasesFactory.getTestArtistResultWantedUrls());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "ViewReleasesModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "UrlModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "UrlModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 8);
    }

    @Test
    public void errorThenSetArtist_displaysArtist()
    {
        controller.setError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 4);

        controller.setLoading(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 4);

        controller.setArtist(ArtistReleasesFactory.getTestArtistResultNoMembers());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "ViewReleasesModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "UrlModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "UrlModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "EmptySpaceModel_");
        assertEquals(copyOfModels.size(), 8);
    }
}
