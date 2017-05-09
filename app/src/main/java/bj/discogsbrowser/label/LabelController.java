package bj.discogsbrowser.label;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.discogsbrowser.R;
import bj.discogsbrowser.epoxy.common.BaseController;
import bj.discogsbrowser.epoxy.common.DividerModel_;
import bj.discogsbrowser.epoxy.common.HeaderModel_;
import bj.discogsbrowser.epoxy.common.ListItemModel_;
import bj.discogsbrowser.epoxy.common.LoadingModel_;
import bj.discogsbrowser.epoxy.common.RetryModel_;
import bj.discogsbrowser.epoxy.common.SubHeaderModel_;
import bj.discogsbrowser.epoxy.label.ViewOnDiscogsModel_;
import bj.discogsbrowser.epoxy.main.InfoTextModel_;
import bj.discogsbrowser.epoxy.main.ViewMoreModel_;
import bj.discogsbrowser.model.label.Label;
import bj.discogsbrowser.model.labelrelease.LabelRelease;
import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.ImageViewAnimator;

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
    private boolean loadingLabelReleases = true;
    private boolean error = false;
    private AnalyticsTracker tracker;

    @Inject
    public LabelController(Context context, LabelContract.View view, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        this.context = context;
        this.view = view;
        this.imageViewAnimator = imageViewAnimator;
        this.tracker = tracker;
    }

    @Override
    protected void buildModels()
    {
        new HeaderModel_()
                .id("header model")
                .context(context)
                .title(title)
                .imageViewAnimator(imageViewAnimator)
                .subtitle(subtitle)
                .imageUrl(imageUrl)
                .addTo(this);

        new DividerModel_()
                .id("divider1")
                .addTo(this);

        if (error)
            new RetryModel_()
                    .errorString("Unable to load Label")
                    .onClick(v -> view.retry())
                    .id("label error")
                    .addTo(this);
        else
        {
            if (loading)
                new LoadingModel_()
                        .imageViewAnimator(imageViewAnimator)
                        .id("label loading")
                        .addTo(this);
            if (loadingLabelReleases)
            {
                new SubHeaderModel_()
                        .subheader("Label Releases")
                        .id("label releases subheader")
                        .addTo(this);

                new LoadingModel_()
                        .imageViewAnimator(imageViewAnimator)
                        .id("releases loading")
                        .addTo(this);
            }

            if (label != null && !loadingLabelReleases)
            {
                if (labelReleases.size() > 0)
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
                else if (labelReleases.size() == 0)
                    new InfoTextModel_()
                            .id("no label releases")
                            .infoText("No label releases")
                            .addTo(this);

                new DividerModel_()
                        .id("releases divider")
                        .addTo(this);

                new ViewOnDiscogsModel_()
                        .id("View on discogs")
                        .onClickListener(v -> view.openLink(label.getUri()))
                        .addTo(this);
            }
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
        this.error = false;
        requestModelBuild();
    }

    public void setLabelReleases(List<LabelRelease> labelReleases)
    {
        this.labelReleases = labelReleases;
        this.loadingLabelReleases = false;
        this.error = false;
        requestModelBuild();
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public void setViewMore(boolean viewMore)
    {
        this.viewMore = viewMore;
        requestModelBuild();
    }

    public void setError(boolean isError)
    {
        this.error = isError;
        if (isError)
        {
            loading = false;
            loadingLabelReleases = false;
            tracker.send(context.getString(R.string.label_activity), context.getString(R.string.label_activity), context.getString(R.string.error), "fetching label", 1L);
        }
        requestModelBuild();
    }

    public void setLoading(boolean isLoading)
    {
        this.loading = isLoading;
        this.loadingLabelReleases = isLoading;
        this.error = false;
        requestModelBuild();
    }
}
