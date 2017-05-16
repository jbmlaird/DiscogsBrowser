package bj.discogsbrowser.artistreleases;

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

import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 09/05/2017.
 * <p>
 * Exact mirror of @{@link bj.discogsbrowser.singlelist.SingleListControllerTest}.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class ArtistReleasesControllerTest
{
    private ArtistReleasesController controller;
    @Mock Context context;
    @Mock ArtistReleasesContract.View view;
    @Mock ImageViewAnimator imageViewAnimator;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        controller = new ArtistReleasesController(context, view, imageViewAnimator);
        controller.requestModelBuild();
    }

    @Test
    public void initialState_Loading()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 3);
    }
}
