package bj.rxjavaexperimentation.artist;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.epoxy.artist.MemberModel_;
import bj.rxjavaexperimentation.epoxy.artist.UrlModel_;
import bj.rxjavaexperimentation.epoxy.artist.ViewReleasesModel_;
import bj.rxjavaexperimentation.epoxy.common.BaseController;
import bj.rxjavaexperimentation.epoxy.common.HeaderModel_;
import bj.rxjavaexperimentation.epoxy.common.SubHeaderModel_;
import bj.rxjavaexperimentation.main.epoxy.DividerModel_;
import bj.rxjavaexperimentation.model.artist.ArtistResult;
import bj.rxjavaexperimentation.model.artist.Member;
import bj.rxjavaexperimentation.utils.WantedUrl;

/**
 * Created by Josh Laird on 21/04/2017.
 */
@Singleton
public class ArtistController extends BaseController
{
    private final ArtistContract.View view;
    private final Context context;
    private ArtistResult artistResult;

    @Inject
    public ArtistController(ArtistContract.View view, Context context)
    {
        this.view = view;
        this.context = context;
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

    public void setArtist(ArtistResult artistResult)
    {
        this.artistResult = artistResult;
        if (artistResult.getImages() != null)
            imageUrl = artistResult.getImages().get(0).getResourceUrl();

        subtitle = artistResult.getProfile();
        requestModelBuild();
    }
}
