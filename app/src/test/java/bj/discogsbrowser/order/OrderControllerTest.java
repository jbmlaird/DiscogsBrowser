package bj.discogsbrowser.order;

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
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 10/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class OrderControllerTest
{
    private OrderController controller;
    @Mock Context context;
    @Mock OrderContract.View view;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock AnalyticsTracker tracker;
    private OrderFactory orderFactory = new OrderFactory();

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new OrderController(context, view, imageViewAnimator, tracker);
        controller.requestModelBuild();
    }

    @Test
    public void initialLoadingState_correct()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 1);
    }

    @Test
    public void errorThenRetry_displaysErrorThenValidData()
    {
        controller.setError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.size(), 1);

        controller.setLoadingOrder(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 1);

        controller.setOrderDetails(orderFactory.getOneItemOrder());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "BuyerModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "OrderReleaseModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TotalModel_");
        assertEquals(copyOfModels.size(), 5);
    }

    @Test
    public void fourItems_displaysFourItems()
    {
        controller.setOrderDetails(orderFactory.getFourItemsOrder());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "BuyerModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "OrderReleaseModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "OrderReleaseModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "OrderReleaseModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "OrderReleaseModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "TotalModel_");
        assertEquals(copyOfModels.size(), 8);
    }
}
