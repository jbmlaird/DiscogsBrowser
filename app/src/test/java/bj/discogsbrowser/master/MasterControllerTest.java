package bj.discogsbrowser.master;

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

import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 10/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class MasterControllerTest
{
    private MasterController controller;
    @Mock Context context;
    @Mock MasterContract.View view;
    @Mock ArtistsBeautifier artistsBeautifer;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock AnalyticsTracker tracker;
    private MasterFactory masterFactory = new MasterFactory();

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new MasterController(view, context, artistsBeautifer, imageViewAnimator, tracker);
        controller.requestModelBuild();
    }

    @Test
    public void initialLoadingState_correct()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 4);
    }

    @Test
    public void masterVersionsEmpty_displaysPaddedCenterText()
    {
        controller.setMasterVersions(masterFactory.getEmptyMasterVersions());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "PaddedCenterTextModel_");
        assertEquals(copyOfModels.size(), 4);
    }

    @Test
    public void errorThenRetryMasterReleases_displaysOnlyMasterReleases()
    {
        controller.setError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.size(), 4);

        controller.setLoading(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 4);

        controller.setMaster(masterFactory.getMaster());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 4);

        controller.setMasterVersions(masterFactory.getTwoMasterVersions());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.size(), 5);
    }

    @Test
    public void masterReleaseOverFive_concatenatesThenExpands()
    {
        controller.setMaster(masterFactory.getMaster());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 4);

        controller.setMasterVersions(masterFactory.getSixMasterVersions());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "ViewMoreModel_");
        assertEquals(copyOfModels.size(), 7);

        controller.setViewAllVersions(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.size(), 9);
    }
}
