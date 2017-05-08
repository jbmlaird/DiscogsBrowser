package bj.discogsbrowser.singlelist;

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
import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.testmodels.TestOrder;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 08/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class SingleListControllerTest
{
    private SingleListController controller;
    @Mock Context context;
    @Mock SingleListContract.View view;
    @Mock ImageViewAnimator imageViewAnimator;
    private ArrayList<RecyclerViewModel> recyclerViewModels;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        controller = new SingleListController(context, view, imageViewAnimator);
        controller.requestModelBuild();

        recyclerViewModels = new ArrayList<>();
        recyclerViewModels.addAll(Arrays.asList(new TestOrder(), new TestOrder(), new TestOrder()));
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

    @Test
    public void setItemsEmptyList_displaysCenterTextModel()
    {
        controller.setItems(Collections.emptyList());

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "CenterTextModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 3);
    }

    @Test
    public void setItems_setsItems()
    {
        controller.setItems(recyclerViewModels);

        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "CardListItemModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "CardListItemModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "CardListItemModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 5);
    }

    @Test
    public void errorThenSetItems_displaysItems()
    {
        controller.setError("");
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "CenterTextModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 3);

        controller.setItems(recyclerViewModels);

        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "CardListItemModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "CardListItemModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "CardListItemModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "SmallEmptySpaceModel_");
        assertEquals(copyOfModels.size(), 5);
    }
}
