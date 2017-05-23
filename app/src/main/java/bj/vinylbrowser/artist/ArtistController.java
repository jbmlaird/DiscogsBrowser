package bj.vinylbrowser.artist;

import android.content.Context;

import bj.vinylbrowser.R;
import bj.vinylbrowser.epoxy.artist.MemberModel_;
import bj.vinylbrowser.epoxy.artist.UrlModel_;
import bj.vinylbrowser.epoxy.artist.ViewReleasesModel_;
import bj.vinylbrowser.epoxy.common.BaseController;
import bj.vinylbrowser.epoxy.common.DividerModel_;
import bj.vinylbrowser.epoxy.common.EmptySpaceModel_;
import bj.vinylbrowser.epoxy.common.LoadingModel_;
import bj.vinylbrowser.epoxy.common.RetryModel_;
import bj.vinylbrowser.epoxy.common.SubHeaderModel_;
import bj.vinylbrowser.model.artist.ArtistResult;
import bj.vinylbrowser.model.artist.ArtistWantedUrl;
import bj.vinylbrowser.model.artist.Member;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;

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
        header
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

            if (artistResult.getArtistWantedUrls() != null && artistResult.getArtistWantedUrls().size() > 0)
            {
                new SubHeaderModel_()
                        .id("links subheader")
                        .subheader("Links")
                        .addTo(this);

                for (ArtistWantedUrl artistWantedUrl : artistResult.getArtistWantedUrls())
                {
                    new UrlModel_()
                            .id("wantedUrl" + artistResult.getArtistWantedUrls().indexOf(artistWantedUrl))
                            .onLinkClick(v -> view.openLink(artistWantedUrl.getUrl()))
                            .fontAwesomeCode(artistWantedUrl.getFontAwesomeString())
                            .friendlyText(artistWantedUrl.getDisplayText())
                            .hexCode(artistWantedUrl.getHexCode())
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
        if (artistResult.getNameVariations() != null && artistResult.getNameVariations().size() > 0)
            title = artistResult.getNameVariations().get(0);
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
        tracker.send(context.getString(R.string.artist_activity), context.getString(R.string.artist_activity), context.getString(R.string.error), "fetching artist", "1");
        requestModelBuild();
    }
}
