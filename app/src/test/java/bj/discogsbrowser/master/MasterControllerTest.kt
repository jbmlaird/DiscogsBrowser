package bj.discogsbrowser.master

import android.content.Context
import android.os.Build
import bj.discogsbrowser.model.master.Master
import bj.discogsbrowser.utils.ArtistsBeautifier
import bj.discogsbrowser.utils.ImageViewAnimator
import bj.discogsbrowser.utils.analytics.AnalyticsTracker
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert
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
@Config(sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP), manifest = Config.NONE)
class MasterControllerTest {
    lateinit var controller: MasterController
    val context: Context = mock()
    val view: MasterContract.View = mock()
    val artistsBeautifer: ArtistsBeautifier = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val tracker: AnalyticsTracker = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = MasterController(view, context, artistsBeautifer, imageViewAnimator, tracker)
        controller.requestModelBuild()
    }

    @Test
    fun initialLoadingState_correct() {
        val copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        Assert.assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        Assert.assertEquals(copyOfModels.size, 4)
    }

    @Test
    fun masterVersionsEmpty_displaysPaddedCenterText() {
        controller.setMasterMasterVersions(emptyList())
        val copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        Assert.assertEquals(copyOfModels[3].javaClass.simpleName, "PaddedCenterTextModel_")
        Assert.assertEquals(copyOfModels.size, 4)
    }

    @Test
    fun errorThenRetryMasterReleases_displaysOnlyMasterReleases() {
        controller.setError(true)
        var copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        Assert.assertEquals(copyOfModels[3].javaClass.simpleName, "RetryModel_")
        Assert.assertEquals(copyOfModels.size, 4)

        controller.setLoading(true)
        copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        Assert.assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        Assert.assertEquals(copyOfModels.size, 4)

        controller.setMaster(Master())
        copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        Assert.assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        Assert.assertEquals(copyOfModels.size, 4)

        controller.setMasterMasterVersions(MasterVersionsFactory.buildMasterVersions(2))
        copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        Assert.assertEquals(copyOfModels[3].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels[4].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels.size, 5)
    }

    @Test
    fun masterReleaseOverFive_concatenatesThenExpands() {
        controller.setMaster(Master())
        var copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        Assert.assertEquals(copyOfModels[3].javaClass.simpleName, "LoadingModel_")
        Assert.assertEquals(copyOfModels.size, 4)

        controller.setMasterMasterVersions(MasterVersionsFactory.buildMasterVersions(6))
        copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        Assert.assertEquals(copyOfModels[3].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels[4].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels[5].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels[6].javaClass.simpleName, "ViewMoreModel_")
        Assert.assertEquals(copyOfModels.size, 7)

        controller.setViewAllVersions(true)
        copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        Assert.assertEquals(copyOfModels[3].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels[4].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels[5].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels[6].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels[7].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels[8].javaClass.simpleName, "ListItemModel_")
        Assert.assertEquals(copyOfModels.size, 9)
    }
}