package bj.discogsbrowser.release;

import android.content.Context;

import com.airbnb.epoxy.EpoxyModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import bj.discogsbrowser.epoxy.release.CollectionWantlistPresenter;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.ImageViewAnimator;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Josh Laird on 10/05/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = LOLLIPOP, manifest = Config.NONE)
public class ReleaseControllerTest
{
    private ReleaseController controller;
    @Mock Context context;
    @Mock ReleaseContract.View view;
    @Mock ArtistsBeautifier artistsBeautifer;
    @Mock ImageViewAnimator imageViewAnimator;
    @Mock CollectionWantlistPresenter presenter;
    @Mock AnalyticsTracker tracker;
    private ReleaseFactory releaseFactory = new ReleaseFactory();

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        controller = new ReleaseController(context, view, artistsBeautifer, imageViewAnimator, presenter, tracker);
        controller.requestModelBuild();
    }

    @Test
    public void initialLoadingState_correct()
    {
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 3);
    }

    @Test
    public void releaseError_displaysRetry()
    {
        controller.setReleaseError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.size(), 3);
    }

    @Test
    public void errorThenSuccessNoTracklistNoVideosEmptyList_displaysSuccess()
    {
        controller.setReleaseError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.size(), 3);

        controller.setReleaseLoading(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 3);

        controller.setRelease(releaseFactory.getReleaseNoLabelNoneForSaleNoTracklistNoVideos());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 5);

        controller.setCollectionWantlistChecked(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "CollectionWantlistModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 5);

        controller.setReleaseListings(Collections.emptyList());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "CollectionWantlistModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "PaddedCenterTextModel_");
        assertEquals(copyOfModels.size(), 5);
    }

    @Test
    public void sixTracksNoVideosViewMore_displaysSuccessAndAllTracks()
    {
        controller.setReleaseError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.size(), 3);

        controller.setReleaseLoading(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 3);

        controller.setRelease(releaseFactory.getReleaseWithLabelSevenTracksNoVideos());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "ViewMoreModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 13);

        controller.setViewFullTracklist(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(7).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 14);
    }

    @Test
    public void errorThenSuccessFiveTracksTwoVideos_displaysSuccess()
    {
        controller.setReleaseError(true);
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.size(), 3);

        controller.setReleaseLoading(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 3);

        controller.setRelease(releaseFactory.getReleaseWithLabelFiveTracksTwoVideos());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 17);
    }

    @Test
    public void loadReleaseCollectionFourListings_displaysAllListingsAndCollection()
    {
        controller.setRelease(releaseFactory.getReleaseWithLabelFiveTracksTwoVideos());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 17);

        controller.setCollectionWantlistChecked(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "CollectionWantlistModel_");
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 17);

        controller.setReleaseListings(releaseFactory.getFourScrapeListings());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "CollectionWantlistModel_");
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "MarketplaceModel_");
        assertEquals(copyOfModels.get(17).getClass().getSimpleName(), "MarketplaceModel_");
        assertEquals(copyOfModels.get(18).getClass().getSimpleName(), "MarketplaceModel_");
        assertEquals(copyOfModels.get(19).getClass().getSimpleName(), "ViewMoreModel_");
        assertEquals(copyOfModels.size(), 20);

        controller.setViewAllListings(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "CollectionWantlistModel_");
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "MarketplaceModel_");
        assertEquals(copyOfModels.get(17).getClass().getSimpleName(), "MarketplaceModel_");
        assertEquals(copyOfModels.get(18).getClass().getSimpleName(), "MarketplaceModel_");
        assertEquals(copyOfModels.get(19).getClass().getSimpleName(), "MarketplaceModel_");
        assertEquals(copyOfModels.size(), 20);
    }

    @Test
    public void releaseCollectionError_displaysCollectionError()
    {
        controller.setRelease(releaseFactory.getReleaseWithLabelFiveTracksTwoVideos());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 17);

        controller.setCollectionWantlistError(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "RetryModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 17);

        controller.setCollectionLoading(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 17);

        controller.setCollectionWantlistChecked(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "CollectionWantlistModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 17);
    }

    @Test
    public void listingsErrorRetryEmptyListings_displaysEmptyListings()
    {
        controller.setRelease(releaseFactory.getReleaseWithLabelFiveTracksTwoVideos());
        List<EpoxyModel<?>> copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 17);

        controller.setReleaseListingsError();
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "RetryModel_");
        assertEquals(copyOfModels.size(), 17);

        controller.setListingsLoading(true);
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "LoadingModel_");
        assertEquals(copyOfModels.size(), 17);

        controller.setReleaseListings(releaseFactory.getOneScrapeListing());
        copyOfModels = controller.getAdapter().getCopyOfModels();
        assertEquals(copyOfModels.get(0).getClass().getSimpleName(), "HeaderModel_");
        assertEquals(copyOfModels.get(1).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(2).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(3).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(4).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(5).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(6).getClass().getSimpleName(), "TrackModel_");
        assertEquals(copyOfModels.get(8).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(9).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(10).getClass().getSimpleName(), "LoadingModel_"); // CollectionWantlist loading
        assertEquals(copyOfModels.get(11).getClass().getSimpleName(), "SubHeaderModel_");
        assertEquals(copyOfModels.get(12).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(13).getClass().getSimpleName(), "YouTubeModel_");
        assertEquals(copyOfModels.get(14).getClass().getSimpleName(), "DividerModel_");
        assertEquals(copyOfModels.get(15).getClass().getSimpleName(), "MarketplaceListingsHeader_");
        assertEquals(copyOfModels.get(16).getClass().getSimpleName(), "MarketplaceModel_");
        assertEquals(copyOfModels.size(), 17);
    }
}
