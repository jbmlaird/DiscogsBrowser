package bj.discogsbrowser.master;

import android.content.Context;

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
import bj.discogsbrowser.epoxy.main.ViewMoreModel_;
import bj.discogsbrowser.model.master.Master;
import bj.discogsbrowser.model.version.Version;
import bj.discogsbrowser.utils.AnalyticsTracker;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 25/04/2017.
 */
@Singleton
public class MasterController extends BaseController
{
    private final MasterContract.View mView;
    private Context context;
    private ArtistsBeautifier artistsBeautifier;
    private ImageViewAnimator imageViewAnimator;
    private AnalyticsTracker tracker;
    private boolean loading = true;
    private boolean error = false;
    private List<Version> masterVersions;
    private boolean viewAllVersions;

    @Inject
    public MasterController(MasterContract.View view, Context context, ArtistsBeautifier artistsBeautifier, ImageViewAnimator imageViewAnimator, AnalyticsTracker tracker)
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
        new HeaderModel_()
                .id("header model")
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

        if (masterVersions != null)
        {
            for (Version version : masterVersions)
            {
                new ListItemModel_()
                        .context(context)
                        .imageUrl(version.getThumb())
                        .title(version.getTitle())
                        .subtitle(version.getFormat())
                        .id("version model" + masterVersions.indexOf(version))
                        .onClick(v -> mView.displayRelease(title, version.getId()))
                        .addTo(this);

                if (masterVersions.indexOf(version) == 2 && !viewAllVersions && masterVersions.size() > 3)
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
        if (master.getImages() != null && master.getImages().size() > 0)
            this.imageUrl = master.getImages().get(0).getUri();
        requestModelBuild();
    }

    public void setMasterVersions(List<Version> masterVersions)
    {
        this.masterVersions = masterVersions;
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
        tracker.send(context.getString(R.string.master_activity), context.getString(R.string.master_activity), context.getString(R.string.error), "fetching master", 1L);
        requestModelBuild();
    }

    private void setViewAllVersions(boolean b)
    {
        viewAllVersions = b;
        tracker.send(context.getString(R.string.master_activity), context.getString(R.string.master_activity), context.getString(R.string.clicked), "view all versions", 1L);
        requestModelBuild();
    }
}
