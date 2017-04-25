package bj.rxjavaexperimentation.master;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.epoxy.common.BaseController;
import bj.rxjavaexperimentation.epoxy.common.ListItemModel_;
import bj.rxjavaexperimentation.epoxy.common.SubHeaderModel_;
import bj.rxjavaexperimentation.main.epoxy.DividerModel_;
import bj.rxjavaexperimentation.main.epoxy.ViewMoreModel_;
import bj.rxjavaexperimentation.model.master.Master;
import bj.rxjavaexperimentation.model.version.Version;
import bj.rxjavaexperimentation.utils.ArtistsBeautifier;

/**
 * Created by Josh Laird on 25/04/2017.
 */
@Singleton
public class MasterController extends BaseController
{
    private final MasterContract.View mView;
    private Context context;
    private ArtistsBeautifier artistsBeautifier;
    private boolean error = false;
    private Master master;
    private boolean versionsError = false;
    private List<Version> masterVersions;
    private boolean viewAllVersions;

    @Inject
    public MasterController(MasterContract.View view, Context context, ArtistsBeautifier artistsBeautifier)
    {
        mView = view;
        this.context = context;
        this.artistsBeautifier = artistsBeautifier;
    }

    @Override
    protected void buildModels()
    {
        header
                .context(context)
                .title(title)
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
                            .onClickListener(v -> setViewAllVersions(true))
                            .addTo(this);
                    break;
                }
            }
        }
    }

    private void setViewAllVersions(boolean b)
    {
        viewAllVersions = b;
        requestModelBuild();
    }

    public void setMaster(Master master)
    {
        this.master = master;
        this.subtitle = artistsBeautifier.formatArtists(master.getArtists());
        if (master.getImages() != null && master.getImages().size() > 0)
            this.imageUrl = master.getImages().get(0).getUri();
        requestModelBuild();
    }

    public void setError(boolean b)
    {
        error = b;
        requestModelBuild();
    }

    public void setVersionsError(boolean b)
    {
        versionsError = b;
        requestModelBuild();
    }

    public void setMasterVersions(List<Version> masterVersions)
    {
        this.masterVersions = masterVersions;
        requestModelBuild();
    }
}
