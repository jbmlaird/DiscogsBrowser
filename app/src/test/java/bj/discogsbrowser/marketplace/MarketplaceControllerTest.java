package bj.discogsbrowser.marketplace;

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

import bj.discogsbrowser.model.listing.Listing;
import bj.discogsbrowser.model.user.UserDetails;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.wrappers.NumberFormatWrapper;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 09/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class MarketplaceControllerTest
{
    private MarketplaceController controller;
    @Mock Context context;
    @Mock MarketplaceContract.View view;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock NumberFormatWrapper numberFormatWrapper;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new MarketplaceController(context, view, imageViewAnimator, numberFormatWrapper);
        controller.requestModelBuild();
    }

    @Test
    public void initialLoadingState_correct()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MarketplaceModelTop_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 3);
    }

    @Test
    public void errorThenValid_displaysValid()
    {
        controller.setError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MarketplaceModelTop_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.size(), 3);

        controller.setLoading(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MarketplaceModelTop_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 3);

        controller.setListing(new Listing());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MarketplaceModelTop_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MarketplaceModelCenter_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "WholeLineModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "WholeLineModel_");
        assertEquals(copyOfModels.size(), 7);

        controller.setSellerDetails(new UserDetails()); // This doesn't create any new models
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "MarketplaceModelTop_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "MarketplaceModelCenter_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "WholeLineModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "WholeLineModel_");
        assertEquals(copyOfModels.size(), 7);
    }
}
