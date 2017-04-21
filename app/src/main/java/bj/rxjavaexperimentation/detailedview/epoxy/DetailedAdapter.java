package bj.rxjavaexperimentation.detailedview.epoxy;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.common.BaseAdapter;
import bj.rxjavaexperimentation.detailedview.DetailedContract;
import bj.rxjavaexperimentation.marketplace.MarketplaceListingActivity;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.LabelRelease;
import bj.rxjavaexperimentation.model.listing.ScrapeListing;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;

/**
 * Created by Josh Laird on 07/04/2017.
 * <p>
 * Epoxy adapter to populate RecyclerView.
 * // TODO: Create a delegate
 */
@Singleton
public class DetailedAdapter extends BaseAdapter
{
    private DetailedHeaderModel_ detailedHeaderModel;
    private DetailedLabelModel_ detailedLabelModel;
    private DetailedReleaseModel_ detailedReleaseModel;
    private MarketplaceModel_ marketplaceModel;
    private Context context;
    private ArtistsBeautifier artistsBeautifier;
    private Release release;
    private String title;
    private DetailedContract.View view;

    @Inject
    public DetailedAdapter(DetailedContract.View view, Context context, ArtistsBeautifier artistsBeautifier)
    {
        enableDiffing();
        this.view = view;
        this.context = context;
        this.artistsBeautifier = artistsBeautifier;
    }

    public void addArtist(ArtistResult artist)
    {
        if (artist.getImages() != null)
            detailedHeaderModel.imageUrl(artist.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = artist.getProfile();
        notifyModelChanged(detailedHeaderModel);
    }

    public void addRelease(Release release)
    {
        this.release = release;
        if (release.getImages() != null)
            detailedHeaderModel.imageUrl(release.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = artistsBeautifier.formatArtists(release.getArtists());
        notifyModelChanged(detailedHeaderModel);

        addModel(new TracklistModel_(context, release.getTracklist()));
        marketplaceModel = new MarketplaceModel_(context, this);
        marketplaceModel.lowestPrice(release.getLowestPriceString());
        marketplaceModel.numberForSale(String.valueOf(release.getNumForSale()));
        marketplaceModel.title(release.getTitle());
        addModel(marketplaceModel);
//        detailedReleaseModel = new DetailedReleaseModel_();
//        detailedReleaseModel.tracklist(release.getTracklist());
    }


    public void addMaster(Master master)
    {
        if (master.getImages() != null)
            detailedHeaderModel.imageUrl(master.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = artistsBeautifier.formatArtists(master.getArtists());
        notifyModelChanged(detailedHeaderModel);
    }

    public void addLabel(Label label)
    {
        if (label.getImages() != null)
            detailedHeaderModel.imageUrl(label.getImages().get(0).getResourceUrl());
        detailedHeaderModel.subtitle = label.getProfile();
        notifyModelChanged(detailedHeaderModel);

        detailedLabelModel = new DetailedLabelModel_(context, view);
        detailedLabelModel.discogsUrl(label.getUri());
        detailedLabelModel.labelId(label.getId());
        detailedLabelModel.labelName(label.getProfile());
        addModel(detailedLabelModel);
    }

    public void addLabelReleases(List<LabelRelease> labelReleases)
    {
        detailedLabelModel.labelReleases = labelReleases;
        notifyModelChanged(detailedLabelModel);
    }

    public void setReleaseListings(ArrayList<ScrapeListing> scrapeListings)
    {
        if (scrapeListings.size() > 0)
        {
            marketplaceModel.listings(scrapeListings);
            notifyModelChanged(marketplaceModel);
        }
        else
            marketplaceModel.noListings();
    }

    public void setReleaseListingsError()
    {
        marketplaceModel.listingsError();
    }

    public void displayListingInformation(ScrapeListing scrapeListing)
    {
        Intent intent = new Intent(context, MarketplaceListingActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("artist", artistsBeautifier.formatArtists(release.getArtists()));
        intent.putExtra("seller", scrapeListing.getSellerName());
        intent.putExtra("sellerRating", scrapeListing.getSellerRating());
        intent.putExtra("id", scrapeListing.getMarketPlaceId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void setHeader(String title)
    {
        this.title = title;
        detailedHeaderModel = new DetailedHeaderModel_(context)
                .title(title);
        addModel(detailedHeaderModel);
    }
}
