package bj.vinylbrowser.master;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import bj.vinylbrowser.R;
import bj.vinylbrowser.epoxy.common.BaseEpxController;
import bj.vinylbrowser.epoxy.common.DividerModel_;
import bj.vinylbrowser.epoxy.common.ListItemModel_;
import bj.vinylbrowser.epoxy.common.LoadingModel_;
import bj.vinylbrowser.epoxy.common.PaddedCenterTextModel_;
import bj.vinylbrowser.epoxy.common.RetryModel_;
import bj.vinylbrowser.epoxy.common.SubHeaderModel_;
import bj.vinylbrowser.epoxy.main.ViewMoreModel_;
import bj.vinylbrowser.model.master.Master;
import bj.vinylbrowser.model.version.MasterVersion;
import bj.vinylbrowser.utils.ArtistsBeautifier;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;

/**
 * Created by Josh Laird on 25/04/2017.
 */
public class MasterEpxController extends BaseEpxController
{
    private final MasterContract.View mView;
    private Context context;
    private ArtistsBeautifier artistsBeautifier;
    private ImageViewAnimator imageViewAnimator;
    private AnalyticsTracker tracker;
    private boolean loading = true;
    private boolean error = false;
    private List<MasterVersion> masterMasterVersions;
    private boolean viewAllVersions;

    public MasterEpxController(MasterContract.View view, Context context, ArtistsBeautifier artistsBeautifier, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
    {
        mView = view;
        this.context = context;
        this.artistsBeautifier = artistsBeautifier;
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
                .id("header divider")
                .addTo(this);

        new SubHeaderModel_()
                .subheader("Versions")
                .id("subheader")
                .addTo(this);

        new LoadingModel_()
                .imageViewAnimator(imageViewAnimator)
                .id("loading model")
                .addIf(loading, this);

        new RetryModel_()
                .id("error model")
                .onClick(v -> mView.retry())
                .errorString("Unable to load Master")
                .addIf(error, this);

        if (masterMasterVersions != null)
        {
            if (masterMasterVersions.size() == 0)
                new PaddedCenterTextModel_()
                        .text("No releases for this Master")
                        .id("no master versions")
                        .addTo(this);
            else
                for (MasterVersion masterVersion : masterMasterVersions)
                {
                    new ListItemModel_()
                            .context(context)
                            .imageUrl(masterVersion.getThumb())
                            .title(masterVersion.getTitle())
                            .subtitle(masterVersion.getFormat())
                            .id("version model" + masterMasterVersions.indexOf(masterVersion))
                            .onClick(v -> mView.displayRelease(title, masterVersion.getId()))
                            .addTo(this);

                    if (masterMasterVersions.indexOf(masterVersion) == 2 && !viewAllVersions && masterMasterVersions.size() > 3)
                    {
                        new ViewMoreModel_()
                                .id("view all")
                                .title("View all versions")
                                .textSize(18f)
                                .onClickListener(v -> setViewAllVersions(true))
                                .addTo(this);
                        break;
                    }
                }
        }
    }

    public void setMaster(Master master)
    {
        this.subtitle = artistsBeautifier.formatArtists(master.getArtists());
        this.title = master.getTitle();
        if (master.getImages().size() > 0)
            this.imageUrl = master.getImages().get(0).getUri();
        this.error = false;
        requestModelBuild();
    }

    public void setMasterMasterVersions(List<MasterVersion> masterMasterVersions)
    {
        this.masterMasterVersions = masterMasterVersions;
        this.error = false;
        this.loading = false;
        requestModelBuild();
    }

    public void setLoading(boolean b)
    {
        loading = b;
        error = false;
        requestModelBuild();
    }

    public void setError(boolean b)
    {
        error = b;
        loading = false;
        tracker.send(context.getString(R.string.master_activity), context.getString(R.string.master_activity), context.getString(R.string.error), "fetching master", "1");
        requestModelBuild();
    }

    @VisibleForTesting()
    public void setViewAllVersions(boolean b)
    {
        viewAllVersions = b;
        tracker.send(context.getString(R.string.master_activity), context.getString(R.string.master_activity), context.getString(R.string.clicked), "view all versions", "1");
        requestModelBuild();
    }
}
