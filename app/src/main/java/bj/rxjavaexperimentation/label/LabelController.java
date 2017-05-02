package bj.rxjavaexperimentation.label;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.epoxy.common.BaseController;
import bj.rxjavaexperimentation.epoxy.common.DividerModel_;
import bj.rxjavaexperimentation.epoxy.common.ErrorModel_;
import bj.rxjavaexperimentation.epoxy.common.HeaderModel_;
import bj.rxjavaexperimentation.epoxy.common.ListItemModel_;
import bj.rxjavaexperimentation.epoxy.common.LoadingModel_;
import bj.rxjavaexperimentation.epoxy.label.ViewOnDiscogsModel_;
import bj.rxjavaexperimentation.epoxy.main.ViewMoreModel_;
import bj.rxjavaexperimentation.model.label.Label;
import bj.rxjavaexperimentation.model.labelrelease.LabelRelease;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class LabelController extends BaseController
{
    private Context context;
    private LabelContract.View view;
    private ImageViewAnimator imageViewAnimator;
    private Label label;
    private List<LabelRelease> labelReleases = new ArrayList<>();
    private boolean viewMore = false;
    private boolean loading = true;
    private boolean error = false;

    @Inject
    public LabelController(Context context, LabelContract.View view, ImageViewAnimator imageViewAnimator)
    {
        this.context = context;
        this.view = view;
        this.imageViewAnimator = imageViewAnimator;
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

        if (loading)
            new LoadingModel_()
                    .imageViewAnimator(imageViewAnimator)
                    .id("label loading")
                    .addTo(this);

        if (error)
            new ErrorModel_()
                    .errorString("Unable to load Label")
                    .onClick(v -> view.retry())
                    .id("label error")
                    .addTo(this);

        if (label != null && !error)
        {
            if (labelReleases.size() > 0)
            {
                for (LabelRelease labelRelease : labelReleases)
                {
                    new ListItemModel_()
                            .id("labelrelease" + labelReleases.indexOf(labelRelease))
                            .subtitle(labelRelease.getArtist())
                            .imageUrl(labelRelease.getThumb())
                            .title(labelRelease.getTitle() + " (" + labelRelease.getCatno() + ")")
                            .context(context)
                            .onClick(v -> view.displayRelease(labelRelease.getId(), labelRelease.getTitle()))
                            .addTo(this);

                    if (labelReleases.indexOf(labelRelease) == 4 && !viewMore && labelReleases.size() > 5)
                    {
                        new ViewMoreModel_()
                                .id("view all")
                                .title("View all label releases")
                                .textSize(18f)
                                .onClickListener(v -> setViewMore(true))
                                .addTo(this);
                        break;
                    }
                }
                new DividerModel_()
                        .id("releases divider")
                        .addTo(this);
            }

            new ViewOnDiscogsModel_()
                    .id("View on discogs")
                    .onClickListener(v -> view.openLink(label.getUri()))
                    .addTo(this);
        }
    }

    public void setLabel(Label label)
    {
        this.label = label;
        if (label.getImages() != null)
            this.imageUrl = label.getImages().get(0).getUri();
        this.title = label.getName();
        this.subtitle = label.getProfile();
        this.loading = false;
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

    public void setError(boolean isError)
    {
        this.error = isError;
        if (isError)
            loading = false;
        requestModelBuild();
    }

    public void setLoading(boolean isLoading)
    {
        this.loading = isLoading;
        this.error = false;
        requestModelBuild();
    }
}
