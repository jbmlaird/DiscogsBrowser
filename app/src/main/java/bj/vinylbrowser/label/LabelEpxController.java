package bj.vinylbrowser.label;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import bj.vinylbrowser.R;
import bj.vinylbrowser.epoxy.common.BaseEpxController;
import bj.vinylbrowser.epoxy.common.DividerModel_;
import bj.vinylbrowser.epoxy.common.ListItemModel_;
import bj.vinylbrowser.epoxy.common.LoadingModel_;
import bj.vinylbrowser.epoxy.common.RetryModel_;
import bj.vinylbrowser.epoxy.common.SmallEmptySpaceModel_;
import bj.vinylbrowser.epoxy.common.SubHeaderModel_;
import bj.vinylbrowser.epoxy.common.WholeLineModel_;
import bj.vinylbrowser.epoxy.main.InfoTextModel_;
import bj.vinylbrowser.epoxy.main.ViewMoreModel_;
import bj.vinylbrowser.model.common.Label;
import bj.vinylbrowser.model.labelrelease.LabelRelease;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class LabelEpxController extends BaseEpxController
{
    private Context context;
    private LabelContract.View view;
    private ImageViewAnimator imageViewAnimator;
    Label label;
    List<LabelRelease> labelReleases = new ArrayList<>();
    private boolean viewMore = false;
    private boolean loading = true;
    private boolean loadingLabelReleases = true;
    private boolean error = false;
    private AnalyticsTracker tracker;

    public LabelEpxController(Context context, LabelContract.View view, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        this.context = context;
        this.view = view;
        this.imageViewAnimator = imageViewAnimator;
        this.tracker = tracker;
    }

    @Override
    protected void buildModels()
    {
        header
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
                        int i = labelReleases.indexOf(labelRelease);
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

                new WholeLineModel_()
                        .id("View on discogs")
                        .text("View on Discogs")
                        .onClickListener(v -> view.openLink(label.getUri()))
                        .addTo(this);
            }
        }
        new SmallEmptySpaceModel_()
                .id("end of epxController model")
                .addTo(this);
    }

    public void setLabel(Label label)
    {
        this.label = label;
        if (label.getImages().size() > 0)
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

    @VisibleForTesting
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
            tracker.send(context.getString(R.string.label_activity), context.getString(R.string.label_activity), context.getString(R.string.error), "fetching label", "1");
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
