package bj.vinylbrowser.release;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import bj.vinylbrowser.R;
import bj.vinylbrowser.epoxy.common.BaseEpxController;
import bj.vinylbrowser.epoxy.common.DividerModel_;
import bj.vinylbrowser.epoxy.common.LoadingModel_;
import bj.vinylbrowser.epoxy.common.PaddedCenterTextModel_;
import bj.vinylbrowser.epoxy.common.RetryModel_;
import bj.vinylbrowser.epoxy.common.SubHeaderModel_;
import bj.vinylbrowser.epoxy.main.ViewMoreModel_;
import bj.vinylbrowser.epoxy.release.CollectionWantlistModel_;
import bj.vinylbrowser.epoxy.release.CollectionWantlistPresenter;
import bj.vinylbrowser.epoxy.release.MarketplaceListingsHeader_;
import bj.vinylbrowser.epoxy.release.MarketplaceModel_;
import bj.vinylbrowser.epoxy.release.TrackModel_;
import bj.vinylbrowser.epoxy.release.YouTubeModel_;
import bj.vinylbrowser.main.MainPresenter;
import bj.vinylbrowser.model.common.Label;
import bj.vinylbrowser.model.listing.ScrapeListing;
import bj.vinylbrowser.model.release.Release;
import bj.vinylbrowser.model.release.Track;
import bj.vinylbrowser.model.release.Video;
import bj.vinylbrowser.utils.ArtistsBeautifier;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;

/**
 * Created by Josh Laird on 24/04/2017.
 */
public class ReleaseEpxController extends BaseEpxController
{
    private final Context context;
    private final ReleaseContract.View view;
    private ArtistsBeautifier artistsBeautifier;
    private ImageViewAnimator imageViewAnimator;
    private CollectionWantlistPresenter presenter;
    Release release;
    List<ScrapeListing> releaseListings;
    private boolean viewFullTracklist = false;
    private boolean releaseLoading = true;
    private boolean releaseError = false;
    private boolean listingsLoading = true;
    private boolean viewAllListings = false;
    private boolean collectionWantlistChecked;
    private boolean collectionWantlistError;
    private boolean listingsError;
    private boolean collectionLoading = true;
    private AnalyticsTracker tracker;
    MainPresenter mainPresenter;

    public ReleaseEpxController(Context context, ReleaseContract.View view, ArtistsBeautifier artistsBeautifier, ImageViewAnimator imageViewAnimator,
                                CollectionWantlistPresenter presenter, AnalyticsTracker tracker, MainPresenter mainPresenter)
    {
        this.context = context;
        this.view = view;
        this.artistsBeautifier = artistsBeautifier;
        this.imageViewAnimator = imageViewAnimator;
        this.presenter = presenter;
        this.tracker = tracker;
        this.mainPresenter = mainPresenter;
    }

    @Override
    protected void buildModels()
    {
        header
                .context(context)
                .title(title)
                .subtitle(subtitle)
                .imageUrl(imageUrl)
                .imageViewAnimator(imageViewAnimator)
                .addTo(this);

        new DividerModel_()
                .id("header_divider")
                .addTo(this);

        new LoadingModel_()
                .imageViewAnimator(imageViewAnimator)
                .id("release loading")
                .addIf(releaseLoading, this);

        new RetryModel_()
                .errorString("Unable to load release")
                .onClick(v -> view.retry())
                .id("release loading")
                .addIf(releaseError, this);

        if (release != null)
        {
            if (release.getTracklist() != null && release.getTracklist().size() > 0)
            {
                for (Track track : release.getTracklist())
                {
                    if (!track.getTitle().equals(""))
                    {
                        new TrackModel_()
                                .id("track" + release.getTracklist().indexOf(track))
                                .trackName(track.getTitle())
                                .trackNumber(track.getPosition())
                                .addTo(this);

                        if (release.getTracklist().indexOf(track) == 4 && release.getTracklist().size() > 5 && !viewFullTracklist)
                        {
                            new ViewMoreModel_()
                                    .id("view more")
                                    .title("View full tracklist")
                                    .textSize(16f)
                                    .onClickListener(v -> setViewFullTracklist(true))
                                    .addTo(this);
                            break;
                        }
                    }
                }

                new DividerModel_()
                        .id("tracklist_divider")
                        .addTo(this);
            }

            for (Label label : release.getLabels())
            {
                new YouTubeModel_()
                        .id("label" + release.getLabels().indexOf(label))
                        .onClick(v -> view.displayLabel(label.getName(), label.getId()))
                        .context(context)
                        .imageUrl(label.getThumb())
                        .title(label.getName())
                        .description(label.getCatno())
                        .addTo(this);
            }

            new LoadingModel_()
                    .id("collectionwantlistloader")
                    .imageViewAnimator(imageViewAnimator)
                    .addIf(collectionLoading && !collectionWantlistError, this);

            new CollectionWantlistModel_()
                    .id("collectionwantlist")
                    .context(context)
                    .releaseId(release.getId())
                    .instanceId(release.getInstanceId())
                    .inCollection(release.isInCollection())
                    .inWantlist(release.isInWantlist())
                    .presenter(presenter)
                    .addIf(collectionWantlistChecked, this);

            new RetryModel_()
                    .errorString("Unable to check Collection/Wantlist")
                    .id("Collection error")
                    .onClick(v -> view.retryCollectionWantlist())
                    .addIf(collectionWantlistError, this);

            if (release.getVideos().size() > 0)
            {
                new SubHeaderModel_()
                        .id("youtube subheader")
                        .subheader("YouTube videos")
                        .addTo(this);

                for (Video video : release.getVideos())
                {
                    if (!video.getUri().equals(""))
                    {
                        try
                        {
                            String youtubeId = video.getUri().split("=")[1];
                            new YouTubeModel_()
                                    .onClick(v -> mainPresenter.addVideo(video))
                                    .imageUrl("https://img.youtube.com/vi/" + youtubeId + "/default.jpg")
                                    .context(context)
                                    .id("youtube" + release.getVideos().indexOf(video))
                                    .title(video.getTitle())
                                    .description(video.getDescription())
                                    .addTo(this);
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            e.printStackTrace();
                            // YouTube video returned has invalid URL
                        }
                    }
                }

                new DividerModel_()
                        .id("video_divider")
                        .addTo(this);
            }

            new MarketplaceListingsHeader_()
                    .id("marketplace listings header")
                    .lowestPrice(release.getLowestPriceString())
                    .numForSale(String.valueOf(release.getNumForSale()))
                    .addTo(this);

            new LoadingModel_()
                    .id("marketplace loader")
                    .imageViewAnimator(imageViewAnimator)
                    .addIf(listingsLoading, this);

            new RetryModel_()
                    .id("listings error")
                    .errorString("Unable to fetch listings")
                    .onClick(v -> view.retryListings())
                    .addIf(listingsError, this);

            if (releaseListings != null && !listingsLoading)
            {
                if (releaseListings.size() == 0 && !listingsError)
                    new PaddedCenterTextModel_()
                            .id("no listings")
                            .text("No 12\" for sale")
                            .addTo(this);
                else
                    for (ScrapeListing scrapeListing : releaseListings)
                    {
                        new MarketplaceModel_()
                                .id("release" + releaseListings.indexOf(scrapeListing))
                                .sleeve(scrapeListing.getSleeveCondition())
                                .media(scrapeListing.getMediaCondition())
                                .shipsFrom(scrapeListing.getShipsFrom())
                                .sellerName(scrapeListing.getSellerName())
                                .sellerRating(scrapeListing.getSellerRating())
                                .convertedPrice(scrapeListing.getConvertedPrice())
                                .price(scrapeListing.getPrice())
                                .onClickListener(v -> view.displayListingInformation(title, subtitle, scrapeListing))
                                .addTo(this);

                        if (releaseListings.indexOf(scrapeListing) == 2 && !viewAllListings && releaseListings.size() > 3)
                        {
                            new ViewMoreModel_()
                                    .id("view all listings")
                                    .title("View all listings")
                                    .textSize(18f)
                                    .onClickListener(v -> setViewAllListings(true))
                                    .addTo(this);
                            break;
                        }
                    }
            }
        }
    }

    public void setRelease(Release release)
    {
        this.release = release;
        if (release.getImages().size() > 0)
            this.imageUrl = release.getImages().get(0).getUri();
        title = release.getTitle();
        subtitle = artistsBeautifier.formatArtists(release.getArtists());
        releaseLoading = false;
        releaseError = false;
        requestModelBuild();
    }

    public void setReleaseListings(List<ScrapeListing> releaseListings)
    {
        this.releaseListings = releaseListings;
        listingsLoading = false;
        listingsError = false;
        requestModelBuild();
    }

    public void setReleaseListingsError()
    {
        listingsError = true;
        listingsLoading = false;
        tracker.send(context.getString(R.string.release_activity), context.getString(R.string.release_activity), context.getString(R.string.error), "release listings", "1");
        requestModelBuild();
    }

    @VisibleForTesting()
    public void setViewFullTracklist(boolean viewFullTracklist)
    {
        this.viewFullTracklist = viewFullTracklist;
        requestModelBuild();
    }

    @VisibleForTesting()
    public void setViewAllListings(boolean viewFullListings)
    {
        this.viewAllListings = viewFullListings;
        requestModelBuild();
    }

    public void setCollectionWantlistChecked(boolean b)
    {
        collectionWantlistChecked = b;
        this.collectionWantlistError = false;
        this.collectionLoading = false;
        requestModelBuild();
    }

    public void setCollectionWantlistError(boolean collectionWantlistError)
    {
        this.collectionWantlistError = collectionWantlistError;
        this.collectionLoading = false;
        tracker.send(context.getString(R.string.release_activity), context.getString(R.string.release_activity), context.getString(R.string.error), "collection", "1");
        requestModelBuild();
    }

    public void setCollectionLoading(boolean collectionLoading)
    {
        this.collectionLoading = collectionLoading;
        this.collectionWantlistError = false;
        requestModelBuild();
    }

    public void setReleaseLoading(boolean releaseLoading)
    {
        this.releaseLoading = releaseLoading;
        this.collectionLoading = true;
        this.listingsLoading = true;
        this.collectionWantlistError = false;
        this.listingsError = false;
        releaseError = false;
        requestModelBuild();
    }

    public void setReleaseError(boolean releaseError)
    {
        this.releaseError = releaseError;
        this.collectionWantlistError = true;
        this.listingsError = true;
        collectionLoading = false;
        releaseLoading = false;
        listingsLoading = false;
        tracker.send(context.getString(R.string.release_activity), context.getString(R.string.release_activity), context.getString(R.string.error), "release", "1");
        requestModelBuild();
    }

    public void setListingsLoading(boolean listingsLoading)
    {
        this.listingsLoading = listingsLoading;
        listingsError = false;
        requestModelBuild();
    }

    public Release getRelease()
    {
        return release;
    }
}
