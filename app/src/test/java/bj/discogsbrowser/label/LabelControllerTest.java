package bj.discogsbrowser.label;

import android.content.Context;

import com.airbnb.epoxy.EpoxyModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.model.common.Label;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 09/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class LabelControllerTest
{
    private LabelController controller;
    @Mock Context context;
    @Mock LabelContract.View view;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock AnalyticsTracker tracker;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        controller = new LabelController(context, view, imageViewAnimator, tracker);
        controller.requestModelBuild();
    }

    @Test
    public void initialState_Loading()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 6);
    }

    @Test
    public void setError_displaysError()
    {
        controller.setError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 4);
    }

    @Test
    public void errorThenSuccess_displaysSuccessNoError()
    {
        controller.setError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 4);

        controller.setLoading(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 6);

        controller.setLabel(new Label());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 5);
    }

    @Test
    public void setLabelNoReleases_displaysLabelNoReleases()
    {
        controller.setLabel(new Label());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 5);

        controller.setLabelReleases(Collections.emptyList());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "InfoTextModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "WholeLineModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 6);
    }

    @Test
    public void setLabelWithReleases_displaysLabelWithReleases()
    {
        controller.setLabel(new Label());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 5);

        controller.setLabelReleases(LabelFactory.buildNumberOfLabelReleases(3));
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "WholeLineModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 8);
    }

    @Test
    public void setLabelWithReleasesOverFive_conctenatesThenDisplaysAll()
    {
        controller.setLabel(new Label());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 5);

        controller.setLabelReleases(LabelFactory.buildNumberOfLabelReleases(6));
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "ViewMoreModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "WholeLineModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 11);

        controller.setViewMore(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "ListItemModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "WholeLineModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 11);
    }
}
