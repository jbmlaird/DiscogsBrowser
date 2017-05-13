package bj.discogsbrowser.artist;

import android.content.Context;

import bj.discogsbrowser.R;
import bj.discogsbrowser.epoxy.artist.MemberModel_;
import bj.discogsbrowser.epoxy.artist.UrlModel_;
import bj.discogsbrowser.epoxy.artist.ViewReleasesModel_;
import bj.discogsbrowser.epoxy.common.BaseController;
import bj.discogsbrowser.epoxy.common.DividerModel_;
import bj.discogsbrowser.epoxy.common.EmptySpaceModel_;
import bj.discogsbrowser.epoxy.common.HeaderModel_;
import bj.discogsbrowser.epoxy.common.LoadingModel_;
import bj.discogsbrowser.epoxy.common.RetryModel_;
import bj.discogsbrowser.epoxy.common.SubHeaderModel_;
import bj.discogsbrowser.model.artist.ArtistResult;
import bj.discogsbrowser.model.artist.Member;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.WantedUrl;

/**
 * Created by Josh Laird on 21/04/2017.
 */
public class ArtistController extends BaseController
{
    private final ArtistContract.View view;
    private final Context context;
    private ImageViewAnimator imageViewAnimator;
    private ArtistResult artistResult;
    private boolean loading = true;
    private boolean error;
    private AnalyticsTracker tracker;

    public ArtistController(ArtistContract.View view, Context context, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        this.view = view;
        this.context = context;
        this.imageViewAnimator = imageViewAnimator;
        this.tracker = tracker;
    }

    @Override
    protected void buildModels()
    {
        new HeaderModel_()
                .id("header model")
                .context(context)
                .imageViewAnimator(imageViewAnimator)
                .title(title)
                .subtitle(subtitle)
                .imageUrl(imageUrl)
                .addTo(this);

        new DividerModel_()
                .id("divider1")
                .addTo(this);

        new LoadingModel_()
                .imageViewAnimator(imageViewAnimator)
                .id("artist loading")
                .addIf(loading, this);

        new RetryModel_()
                .errorString("Unable to load Artist")
                .onClick(v -> view.retry())
                .id("error model")
                .addIf(error, this);

        if (artistResult != null)
        {
            if (artistResult.getMembers() != null && artistResult.getMembers().size() > 0)
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

            if (artistResult.getWantedUrls() != null && artistResult.getWantedUrls().size() > 0)
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

            }
        }
        new EmptySpaceModel_()
                .id("empty space model")
                .addTo(this);
    }

    public void setArtist(ArtistResult artistResult)
    {
        this.artistResult = artistResult;
        if (artistResult.getImages() != null)
            imageUrl = artistResult.getImages().get(0).getResourceUrl();
        if (artistResult.getNamevariations() != null && artistResult.getNamevariations().size() > 0)
            title = artistResult.getNamevariations().get(0);
        subtitle = artistResult.getProfile();
        this.loading = false;
        this.error = false;
        requestModelBuild();
    }

    public void setLoading(boolean isLoading)
    {
        this.loading = isLoading;
        this.error = false;
        requestModelBuild();
    }

    public void setError(boolean isError)
    {
        this.error = isError;
        this.loading = false;
        tracker.send(context.getString(R.string.artist_activity), context.getString(R.string.artist_activity), context.getString(R.string.error), "fetching artist", 1L);
        requestModelBuild();
    }
}
