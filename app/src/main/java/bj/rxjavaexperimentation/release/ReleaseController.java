package bj.rxjavaexperimentation.release;

import android.content.Context;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.epoxy.common.BaseController;
import bj.rxjavaexperimentation.epoxy.common.DividerModel_;
import bj.rxjavaexperimentation.epoxy.common.ErrorModel_;
import bj.rxjavaexperimentation.epoxy.common.HeaderModel_;
import bj.rxjavaexperimentation.epoxy.common.LoadingModel_;
import bj.rxjavaexperimentation.epoxy.common.SubHeaderModel_;
import bj.rxjavaexperimentation.epoxy.main.ViewMoreModel_;
import bj.rxjavaexperimentation.epoxy.release.CollectionWantlistModel_;
import bj.rxjavaexperimentation.epoxy.release.MarketplaceListingsHeader_;
import bj.rxjavaexperimentation.epoxy.release.MarketplaceModel_;
import bj.rxjavaexperimentation.epoxy.release.NoListingsModel_;
import bj.rxjavaexperimentation.epoxy.release.TrackModel_;
import bj.rxjavaexperimentation.epoxy.release.YouTubeModel_;
import bj.rxjavaexperimentation.model.listing.ScrapeListing;
import bj.rxjavaexperimentation.model.release.Label;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.model.release.Track;
import bj.rxjavaexperimentation.model.release.Video;
import bj.rxjavaexperimentation.network.DiscogsInteractor;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;
import bj.rxjavaexperimentation.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 24/04/2017.
 */
@Singleton
public class ReleaseController extends BaseController
{
    private final Context context;
    private final ReleaseContract.View view;
    private ArtistsBeautifier artistsBeautifier;
    private ImageViewAnimator imageViewAnimator;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private Release release;
    private ArrayList<ScrapeListing> releaseListings;
    private boolean viewFullTracklist = false;
    private boolean releaseLoading = true;
    private boolean releaseError = false;
    private boolean marketplaceLoading = true;
    private boolean viewAllListings = false;
    private boolean collectionWantlistChecked;
    private boolean collectionError;
    private boolean wantlistError;
    private boolean listingsError;
    private boolean collectionLoading = true;

    @Inject
    public ReleaseController(Context context, ReleaseContract.View view, ArtistsBeautifier artistsBeautifier, ImageViewAnimator imageViewAnimator,
                             DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider)
    {
        this.context = context;
        this.view = view;
        this.artistsBeautifier = artistsBeautifier;
        this.imageViewAnimator = imageViewAnimator;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    @Override
    protected void buildModels()
    {
        new HeaderModel_()
                .id("header model")
                .context(context)
                .title(title)
                .subtitle(subtitle)
                .imageUrl(imageUrl)
                .addTo(this);

        new DividerModel_()
                .id("header_divider")
                .addTo(this);

        new LoadingModel_()
                .imageViewAnimator(imageViewAnimator)
                .id("release loading")
                .addIf(releaseLoading, this);

        new ErrorModel_()
                .errorString("Unable to load release")
                .onClick(v -> view.retry())
                .id("release loading")
                .addIf(releaseError, this);

        if (release != null)
        {
            for (Track track : release.getTracklist())
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

            new DividerModel_()
                    .id("tracklist_divider")
                    .addTo(this);

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
                    .addIf(collectionLoading, this);

            new CollectionWantlistModel_()
                    .id("collectionwantlist")
                    .disposable(new CompositeDisposable())
                    .context(context)
                    .releaseId(release.getId())
                    .instanceId(release.getInstanceId())
                    .inCollection(release.isInCollection())
                    .inWantlist(release.isInWantlist())
                    .mySchedulerProvider(mySchedulerProvider)
                    .discogsInteractor(discogsInteractor)
                    .addIf(collectionWantlistChecked, this);

            new ErrorModel_()
                    .errorString("Unable to check Collection")
                    .id("Collection error")
                    .onClick(v -> view.retryCollectionWantlist())
                    .addIf(collectionError, this);

            new ErrorModel_()
                    .errorString("Unable to check Wantlist")
                    .onClick(v -> view.retryCollectionWantlist())
                    .id("Wantlist error")
                    .addIf(wantlistError, this);

            new SubHeaderModel_()
                    .id("youtube subheader")
                    .subheader("YouTube videos")
                    .addTo(this);

            if (release.getVideos() != null)
            {
                for (Video video : release.getVideos())
                {
                    String youtubeId = video.getUri().split("=")[1];
                    new YouTubeModel_()
                            .onClick(v -> view.launchYouTube(youtubeId))
                            .imageUrl("https://img.youtube.com/vi/" + youtubeId + "/default.jpg")
                            .context(context)
                            .id("youtube" + release.getVideos().indexOf(video))
                            .title(video.getTitle())
                            .description(video.getDescription())
                            .addTo(this);
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
                    .addIf(marketplaceLoading, this);

            new ErrorModel_()
                    .id("listings error")
                    .errorString("Unable to fetch listings")
                    .onClick(v -> view.retryListings())
                    .addIf(listingsError, this);

            if (releaseListings != null)
            {
                if (releaseListings.size() == 0)
                    new NoListingsModel_()
                            .id("no listings")
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
                                    .onClickListener(v -> setViewListings(true))
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
        if (release.getImages() != null)
            this.imageUrl = release.getImages().get(0).getUri();
        title = release.getTitle();
        subtitle = artistsBeautifier.formatArtists(release.getArtists());
        releaseLoading = false;
        releaseError = false;
        requestModelBuild();
    }

    public void setReleaseListings(ArrayList<ScrapeListing> releaseListings)
    {
        this.releaseListings = releaseListings;
        marketplaceLoading = false;
        listingsError = false;
        requestModelBuild();
    }

    public void setReleaseListingsError()
    {
        listingsError = true;
        marketplaceLoading = false;
        requestModelBuild();
    }

    private void setViewFullTracklist(boolean viewFullTracklist)
    {
        this.viewFullTracklist = viewFullTracklist;
        requestModelBuild();
    }

    private void setViewListings(boolean viewFullListings)
    {
        this.viewAllListings = viewFullListings;
        requestModelBuild();
    }

    public void collectionWantlistChecked(boolean b)
    {
        collectionWantlistChecked = b;
        this.collectionError = false;
        this.wantlistError = false;
        this.collectionLoading = false;
        requestModelBuild();
    }

    public void setCollectionError(boolean collectionError)
    {
        this.collectionError = collectionError;
        this.collectionLoading = false;
        requestModelBuild();
    }

    public void setCollectionLoading(boolean collectionLoading)
    {
        this.collectionLoading = collectionLoading;
        this.collectionError = false;
        requestModelBuild();
    }

    public void setWantlistError(boolean wantlistError)
    {
        this.wantlistError = wantlistError;
        this.collectionLoading = false;
        requestModelBuild();
    }

    public void setReleaseLoading(boolean releaseLoading)
    {
        this.releaseLoading = releaseLoading;
        releaseError = false;
        requestModelBuild();
    }

    public void setReleaseError(boolean releaseError)
    {
        this.releaseError = releaseError;
        releaseLoading = false;
        requestModelBuild();
    }

    public void setMarketplaceLoading(boolean marketplaceLoading)
    {
        this.marketplaceLoading = marketplaceLoading;
        listingsError = false;
        requestModelBuild();
    }

    public Release getRelease()
    {
        return release;
    }
}
