package bj.rxjavaexperimentation.artist;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.artist.epoxy.HeaderModel_;
import bj.rxjavaexperimentation.artist.epoxy.MemberModel_;
import bj.rxjavaexperimentation.artist.epoxy.ViewReleasesModel_;
import bj.rxjavaexperimentation.artist.epoxy.SubHeaderModel_;
import bj.rxjavaexperimentation.artist.epoxy.UrlModel_;
import bj.rxjavaexperimentation.main.epoxy.DividerModel_;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.artist.Member;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.LabelRelease;
import bj.rxjavaexperimentation.model.listing.ScrapeListing;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.release.Release;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;
import bj.rxjavaexperimentation.utils.WantedUrl;

/**
 * Created by Josh Laird on 21/04/2017.
 */
@Singleton
public class ArtistController extends EpoxyController
{
    private final ArtistContract.View view;
    private final Context context;
    private final ArtistsBeautifier artistsBeautifier;
    private String title = "";
    private String subtitle = "";
    private String imageUrl = "";
    private ArtistResult artistResult;

    @Inject
    public ArtistController(ArtistContract.View view, Context context, ArtistsBeautifier artistsBeautifier)
    {
        this.view = view;
        this.context = context;
        this.artistsBeautifier = artistsBeautifier;
    }

    @Override
    protected void buildModels()
    {
        new HeaderModel_()
                .id("header")
                .context(context)
                .title(title)
                .subtitle(subtitle)
                .imageUrl(imageUrl)
                .addTo(this);

        new DividerModel_()
                .id("divider1")
                .addTo(this);

        if (artistResult != null)
        {
            if (artistResult.getMembers() != null)
            {
                new SubHeaderModel_()
                        .id("members subheader")
                        .subheader("Members")
                        .addTo(this);

                for (Member member : artistResult.getMembers())
                {
                    new MemberModel_()
                            .id("member" + artistResult.getMembers().indexOf(member))
                            .name(member.getName())
                            .onClick(v -> view.showMemberDetails(member.getName(), member.getId()))
                            .addTo(this);
                }

                new DividerModel_()
                        .id("divider2")
                        .addTo(this);
            }

            if (artistResult.getReleasesUrl() != null)
            {
                new ViewReleasesModel_()
                        .onViewReleasesClicked(v -> view.showArtistReleases(title, artistResult.getId()))
                        .id("viewReleases")
                        .addTo(this);

                new DividerModel_()
                        .id("releasesDivider")
                        .addTo(this);
            }

            if (artistResult.getWantedUrls() != null)
            {
                new SubHeaderModel_()
                        .id("links subheader")
                        .subheader("Links")
                        .addTo(this);

                for (WantedUrl wantedUrl : artistResult.getWantedUrls())
                {
                    new UrlModel_()
                            .id("wantedUrl" + artistResult.getWantedUrls().indexOf(wantedUrl))
                            .onLinkClick(v -> view.showLink(wantedUrl.getUrl()))
                            .fontAwesomeCode(wantedUrl.getFontAwesomeString())
                            .friendlyText(wantedUrl.getDisplayText())
                            .hexCode(wantedUrl.getHexCode())
                            .addTo(this);
                }

                new DividerModel_()
                        .id("divider3")
                        .addTo(this);
            }
        }
    }

    public void setHeader(String title)
    {
        this.title = title;
    }

    public void setArtist(ArtistResult artistResult)
    {
        this.artistResult = artistResult;
        if (artistResult.getImages() != null)
            imageUrl = artistResult.getImages().get(0).getResourceUrl();

        subtitle = artistResult.getProfile();
        requestModelBuild();
    }

    public void setRelease(Release release)
    {

    }

    public void setMaster(Master master)
    {

    }

    public void setLabel(Label label)
    {

    }

    public void addLabelReleases(List<LabelRelease> labelReleases)
    {
//        detailedLabelModel.labelReleases = labelReleases;
//        notifyModelChanged(detailedLabelModel);
    }

    public void setReleaseListings(ArrayList<ScrapeListing> scrapeListings)
    {
//        if (scrapeListings.size() > 0)
//        {
//            marketplaceModel.listings(scrapeListings);
//            notifyModelChanged(marketplaceModel);
//        }
//        else
//            marketplaceModel.noListings();
    }

    public void setReleaseListingsError()
    {
//        marketplaceModel.listingsError();
    }

    public void displayListingInformation(ScrapeListing scrapeListing)
    {

    }
}
