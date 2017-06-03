package bj.vinylbrowser.main

import android.content.Context
import android.os.Build
import bj.vinylbrowser.main.panel.YouTubePlayerEpxController
import bj.vinylbrowser.main.panel.YouTubePlayerHolder
import bj.vinylbrowser.release.ReleaseFactory
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Josh Laird on 02/06/2017.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP), manifest = Config.NONE)
class YouTubePlayerEpxControllerTest {
    lateinit var controller: YouTubePlayerEpxController
    val context: Context = mock()
    val youTubePlayerHolder: YouTubePlayerHolder = mock()
    val mainPresenter: MainPresenter = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = YouTubePlayerEpxController(context, youTubePlayerHolder, mainPresenter)
        controller.requestModelBuild()
    }

    @Test
    fun initialStateNoVideos_displaysInfoModel() {
        val copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "InfoTextModel_")
        Assert.assertEquals(copyOfModels.size, 2)
    }

    @Test
    fun addTwoVideosThenRemove_updatesCorrectly() {
        controller.addVideo(ReleaseFactory.buildVideo(0))
        controller.addVideo(ReleaseFactory.buildVideo(1))
        var copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "YouTubeListModel_")
        Assert.assertEquals(copyOfModels[2].javaClass.simpleName, "YouTubeListModel_")
        Assert.assertEquals(copyOfModels.size, 3)

        controller.removeNextVideo()
        copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "YouTubeListModel_")
        Assert.assertEquals(copyOfModels.size, 2)

        controller.removeNextVideo()
        copyOfModels = controller.adapter.copyOfModels
        Assert.assertEquals(copyOfModels[0].javaClass.simpleName, "MainTitleModel_")
        Assert.assertEquals(copyOfModels[1].javaClass.simpleName, "InfoTextModel_")
        Assert.assertEquals(copyOfModels.size, 2)
    }
}