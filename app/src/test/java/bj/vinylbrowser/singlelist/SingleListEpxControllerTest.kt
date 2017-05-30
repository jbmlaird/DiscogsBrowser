package bj.vinylbrowser.singlelist

import android.content.Context
import android.os.Build.VERSION_CODES.LOLLIPOP
import bj.vinylbrowser.model.common.RecyclerViewModel
import bj.vinylbrowser.utils.ImageViewAnimator
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Josh Laird on 22/05/2017.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(LOLLIPOP), manifest = Config.NONE)
class SingleListEpxControllerTest {
    lateinit var controller: SingleListEpxController
    val context: Context = mock()
    val view: SingleListContract.View = mock()
    val imageViewAnimator: ImageViewAnimator = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        controller = SingleListEpxController(context, view, imageViewAnimator)
        controller.requestModelBuild()
    }

    @Test
    fun initialState_Loading() {
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 3)
    }

    @Test
    fun setItemsEmptyList_displaysCenterTextModel() {
        controller.setItems(emptyList<RecyclerViewModel>())

        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "CenterTextModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 3)
    }

    @Test
    fun setItems_setsItems() {
        val recyclerViewModels = WantFactory.getThreeWants()
        controller.setItems(recyclerViewModels)

        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "CardListItemModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "CardListItemModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "CardListItemModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 5)
    }

    @Test
    fun errorThenSetItems_displaysItems() {
        controller.setError("")
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "CenterTextModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 3)

        val recyclerViewModels = WantFactory.getThreeWants()
        controller.setItems(recyclerViewModels)

        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "CardListItemModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "CardListItemModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "CardListItemModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 5)
    }
}