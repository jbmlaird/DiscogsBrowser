package bj.vinylbrowser.label

import android.content.Context
import android.os.Build.VERSION_CODES.LOLLIPOP
import bj.vinylbrowser.model.common.Label
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations.initMocks
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Josh Laird on 21/05/2017.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(LOLLIPOP), manifest = Config.NONE)
class LabelEpxControllerTest {
    lateinit var controller: LabelEpxController
    val context: Context = mock()
    val view: LabelContract.View = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val tracker: AnalyticsTracker = mock()

    @Before
    fun setUp() {
        initMocks(this)
        controller = LabelEpxController(context, view, imageViewAnimator, tracker)
        controller.requestModelBuild()
    }

    @Test
    fun initialState_Loading() {
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 6)
    }

    @Test
    fun setError_displaysError() {
        controller.setError(true)
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 4)
    }

    @Test
    fun errorThenSuccess_displaysSuccessNoError() {
        controller.setError(true)
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 4)

        controller.setLoading(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 6)

        controller.setLabel(Label())
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 5)
    }

    @Test
    fun setLabelNoReleases_displaysLabelNoReleases() {
        controller.setLabel(Label())
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 5)

        controller.setLabelReleases(emptyList())
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "InfoTextModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "WholeLineModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 6)
    }

    @Test
    fun setLabelWithReleases_displaysLabelWithReleases() {
        controller.setLabel(Label())
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 5)

        controller.setLabelReleases(LabelFactory.buildNumberOfLabelReleases(3))
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "WholeLineModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 8)
    }

    @Test
    fun setLabelWithReleasesOverFive_conctenatesThenDisplaysAll() {
        controller.setLabel(Label())
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 5)

        controller.setLabelReleases(LabelFactory.buildNumberOfLabelReleases(6))
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "ViewMoreModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "WholeLineModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 11)

        controller.setViewMore(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "ListItemModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "WholeLineModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 11)
    }
}