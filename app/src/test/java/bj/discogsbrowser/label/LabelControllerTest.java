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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.testmodels.TestOrder;
import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;

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
    private List<RecyclerViewModel> recyclerViewModels = new ArrayList<>();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        controller = new LabelController(context, view, imageViewAnimator, tracker);
        controller.requestModelBuild();

        recyclerViewModels.addAll(Arrays.asList(new TestOrder(), new TestOrder(), new TestOrder()));
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
        assertEquals(copyOfModels.size(), 5);
    }
}
