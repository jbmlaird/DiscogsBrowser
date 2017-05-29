package bj.vinylbrowser.artist

import android.content.Context
import android.os.Build.VERSION_CODES.LOLLIPOP
import bj.vinylbrowser.artistreleases.ArtistResultFactory
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by Josh Laird on 19/05/2017.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(LOLLIPOP), manifest = Config.NONE)
class ArtistControllerTest {
    private lateinit var controller: ArtistEpxController
    val context: Context = mock()
    val view: ArtistContract.View = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val tracker: AnalyticsTracker = mock()

    @Before
    fun setUp() {
        controller = ArtistEpxController(view, context, imageViewAnimator, tracker)
        controller.requestModelBuild()
    }

    @Test
    fun initialLoadingState_correct() {
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 4)
    }

    @Test
    fun setArtistNoMembers_displaysNoMembers() {
        controller.setArtist(ArtistResultFactory.buildArtistResult(0))
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "ViewReleasesModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "UrlModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "UrlModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 8)
    }

    @Test
    fun setArtistTwoMembers_displaysTwoMembers() {
        controller.setArtist(ArtistResultFactory.buildArtistResult(2))
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "MemberModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "MemberModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "ViewReleasesModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "UrlModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "UrlModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 12)
    }

    @Test
    fun setArtistWithWantedUrl_displaysArtistWithWantedUrl() {
        controller.setArtist(ArtistResultFactory.buildArtistResult(0))
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "ViewReleasesModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "UrlModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "UrlModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 8)
    }

    @Test
    fun errorThenSetArtist_displaysArtist() {
        controller.setError(true)
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 4)

        controller.setLoading(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 4)

        controller.setArtist(ArtistResultFactory.buildArtistResult(0))
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "ViewReleasesModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "UrlModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "UrlModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "EmptySpaceModel_")
        assertEquals(copyOfModels.size, 8)
    }
}