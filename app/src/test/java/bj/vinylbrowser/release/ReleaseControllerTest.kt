package bj.vinylbrowser.release

import android.content.Context
import android.os.Build.VERSION_CODES.LOLLIPOP
import bj.vinylbrowser.epoxy.release.CollectionWantlistPresenter
import bj.vinylbrowser.utils.ArtistsBeautifier
import bj.vinylbrowser.utils.ImageViewAnimator
import bj.vinylbrowser.utils.analytics.AnalyticsTracker
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
class ReleaseControllerTest {
    lateinit var controller: ReleaseController
    val context: Context = mock()
    val view: ReleaseContract.View = mock()
    val artistsBeautifier: ArtistsBeautifier = mock()
    val imageViewAnimator: ImageViewAnimator = mock()
    val presenter: CollectionWantlistPresenter = mock()
    val tracker: AnalyticsTracker = mock()
    val id = "releaseId"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = ReleaseController(context, view, artistsBeautifier, imageViewAnimator, presenter, tracker)
        controller.requestModelBuild()
    }

    @Test
    fun initialLoadingState_correct() {
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 3)
    }

    @Test
    fun releaseError_displaysRetry() {
        controller.setReleaseError(true)
        val copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels.size, 3)
    }

    @Test
    fun errorThenSuccessNoTracklistNoVideosEmptyList_displaysSuccess() {
        controller.setReleaseError(true)
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels.size, 3)

        controller.setReleaseLoading(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 3)

        controller.release = ReleaseFactory.getReleaseNoLabelNoneForSaleNoTracklistNoVideos(id)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[3].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 5)

        controller.setCollectionWantlistChecked(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "CollectionWantlistModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[3].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 5)

        controller.setReleaseListings(emptyList())
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "CollectionWantlistModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[3].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "PaddedCenterTextModel_")
        assertEquals(copyOfModels.size, 5)
    }

    @Test
    fun sixTracksNoVideosViewMore_displaysSuccessAndAllTracks() {
        controller.setReleaseError(true)
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels.size, 3)

        controller.setReleaseLoading(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 3)

        controller.release = ReleaseFactory.buildReleaseWithLabelSevenTracksNoVideos(id)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "ViewMoreModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[11].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 13)

        controller.setViewFullTracklist(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[12].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 14)
    }

    @Test
    fun errorThenSuccessFiveTracksTwoVideos_displaysSuccess() {
        controller.setReleaseError(true)
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels.size, 3)

        controller.setReleaseLoading(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 3)

        controller.release = ReleaseFactory.buildReleaseWithLabelFiveTracksTwoVideos(id)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 16)
    }

    @Test
    fun loadReleaseCollectionFourListings_displaysAllListingsAndCollection() {
        controller.release = ReleaseFactory.buildReleaseWithLabelFiveTracksTwoVideos(id)
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 16)

        controller.setCollectionWantlistChecked(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "CollectionWantlistModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 16)

        controller.setReleaseListings(ScrapeListFactory.buildFourEmptyScrapeListing())
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "CollectionWantlistModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "MarketplaceModel_")
        assertEquals(copyOfModels[16].javaClass.simpleName, "MarketplaceModel_")
        assertEquals(copyOfModels[17].javaClass.simpleName, "MarketplaceModel_")
        assertEquals(copyOfModels[18].javaClass.simpleName, "ViewMoreModel_")
        assertEquals(copyOfModels.size, 19)

        controller.setViewAllListings(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "CollectionWantlistModel_")
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "MarketplaceModel_")
        assertEquals(copyOfModels[16].javaClass.simpleName, "MarketplaceModel_")
        assertEquals(copyOfModels[17].javaClass.simpleName, "MarketplaceModel_")
        assertEquals(copyOfModels[18].javaClass.simpleName, "MarketplaceModel_")
        assertEquals(copyOfModels.size, 19)
    }

    @Test
    fun releaseCollectionError_displaysCollectionError() {
        controller.release = ReleaseFactory.buildReleaseWithLabelFiveTracksTwoVideos(id)
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 16)

        controller.setCollectionWantlistError(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "RetryModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 16)

        controller.setCollectionLoading(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 16)

        controller.setCollectionWantlistChecked(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "CollectionWantlistModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 16)
    }

    @Test
    fun listingsErrorRetryEmptyListings_displaysEmptyListings() {
        controller.release = ReleaseFactory.buildReleaseWithLabelFiveTracksTwoVideos(id)
        var copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 16)

        controller.setReleaseListingsError()
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "RetryModel_")
        assertEquals(copyOfModels.size, 16)

        controller.setListingsLoading(true)
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "LoadingModel_")
        assertEquals(copyOfModels.size, 16)

        controller.setReleaseListings(ScrapeListFactory.buildOneEmptyScrapeListing())
        copyOfModels = controller.adapter.copyOfModels
        assertEquals(copyOfModels[0].javaClass.simpleName, "HeaderModel_")
        assertEquals(copyOfModels[1].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[2].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[3].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[4].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[5].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[6].javaClass.simpleName, "TrackModel_")
        assertEquals(copyOfModels[7].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[8].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[9].javaClass.simpleName, "LoadingModel_") // CollectionWantlist loading
        assertEquals(copyOfModels[10].javaClass.simpleName, "SubHeaderModel_")
        assertEquals(copyOfModels[11].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[12].javaClass.simpleName, "YouTubeModel_")
        assertEquals(copyOfModels[13].javaClass.simpleName, "DividerModel_")
        assertEquals(copyOfModels[14].javaClass.simpleName, "MarketplaceListingsHeader_")
        assertEquals(copyOfModels[15].javaClass.simpleName, "MarketplaceModel_")
        assertEquals(copyOfModels.size, 16)
    }
}