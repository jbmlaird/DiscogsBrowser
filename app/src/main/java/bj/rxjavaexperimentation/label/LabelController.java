package bj.rxjavaexperimentation.label;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.artist.epoxy.HeaderModel_;
import bj.rxjavaexperimentation.label.epoxy.LabelReleaseModel_;
import bj.rxjavaexperimentation.label.epoxy.ViewOnDiscogsModel_;
import bj.rxjavaexperimentation.main.epoxy.DividerModel_;
import bj.rxjavaexperimentation.main.epoxy.ViewMoreModel_;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.LabelRelease;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class LabelController extends EpoxyController
{
    private Context context;
    private LabelContract.View view;
    private String title = "";
    private String subtitle = "";
    private String imageUrl = "";
    private Label label;
    private List<LabelRelease> labelReleases = new ArrayList<>();
    private boolean viewMore = false;

    @Inject
    public LabelController(Context context, LabelContract.View view)
    {
        this.context = context;
        this.view = view;
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

        if (labelReleases.size() > 0)
        {
            for (LabelRelease labelRelease : labelReleases)
            {
                new LabelReleaseModel_()
                        .id("labelrelease" + labelReleases.indexOf(labelRelease))
                        .artists(labelRelease.getArtist())
                        .imageUrl(labelRelease.getThumb())
                        .title(labelRelease.getTitle())
                        .context(context)
                        .onLabelClick(v -> view.displayRelease(labelRelease.getId(), labelRelease.getTitle()))
                        .addTo(this);

                if (labelReleases.indexOf(labelRelease) == 4 && !viewMore)
                {
                    new ViewMoreModel_()
                            .id("view all")
                            .title("View all label releases")
                            .onClickListener(v -> setViewMore(true))
                            .addTo(this);
                    break;
                }
            }
        }

        new DividerModel_()
                .id("releases divider")
                .addTo(this);

        new ViewOnDiscogsModel_()
                .id("View on discogs")
                .onClickListener(v -> view.openLink(label.getUri()))
                .addTo(this);
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setLabel(Label label)
    {
        this.label = label;
        if (label.getImages() != null)
            this.imageUrl = label.getImages().get(0).getUri();
        this.subtitle = label.getProfile();
        requestModelBuild();
    }

    public void setLabelReleases(List<LabelRelease> labelReleases)
    {
        this.labelReleases = labelReleases;
        requestModelBuild();
    }

    private void setViewMore(boolean viewMore)
    {
        this.viewMore = viewMore;
        requestModelBuild();
    }
}
