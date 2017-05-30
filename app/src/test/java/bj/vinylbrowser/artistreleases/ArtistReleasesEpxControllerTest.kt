package bj.vinylbrowser.artistreleases

import android.content.Context
import android.os.Build
import bj.vinylbrowser.utils.ImageViewAnimator
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Josh Laird on 21/05/2017.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP), manifest = Config.NONE)
class ArtistReleasesEpxControllerTest {
    lateinit var epxController: ArtistReleasesEpxController
    val context: Context = mock()
    val view: ArtistReleasesContract.View = mock()
    val imageViewAnimator: ImageViewAnimator = mock()

    @Before
    fun setUp() {
        epxController = ArtistReleasesEpxController(context, view, imageViewAnimator)
        epxController.requestModelBuild()
    }

    @Test
    fun initialState_Loading() {
        val copyOfModels = epxController.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SmallEmptySpaceModel_")
        assertEquals(copyOfModels.size, 3)
    }
}