package bj.discogsbrowser.artistreleases;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.epoxy.common.CardListItemModel_;
import bj.discogsbrowser.epoxy.common.CenterTextModel_;
import bj.discogsbrowser.epoxy.common.LoadingModel_;
import bj.discogsbrowser.epoxy.common.SmallEmptySpaceModel_;
import bj.discogsbrowser.model.artistrelease.ArtistRelease;
import bj.discogsbrowser.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 08/05/2017.
 */
public class ArtistReleasesController extends EpoxyController
{
    private final Context context;
    private final ArtistReleasesContract.View view;
    private final ImageViewAnimator imageViewAnimator;
    private List<ArtistRelease> items = new ArrayList<>();
    private boolean error;
    private boolean loading = true;
    private String errorMessage = "";

    @Inject
    public ArtistReleasesController(Context context, ArtistReleasesContract.View view, ImageViewAnimator imageViewAnimator)
    {
        this.context = context;
        this.view = view;
        this.imageViewAnimator = imageViewAnimator;
    }

    @Override
    protected void buildModels()
    {
        new SmallEmptySpaceModel_()
                .id("emptyspace")
                .addTo(this);

        new LoadingModel_()
                .imageViewAnimator(imageViewAnimator)
                .id("single list loading")
                .addIf(loading, this);

        if (error)
            new CenterTextModel_()
                    .id("error model")
                    .text("error")
                    .addTo(this);
        else if (items.size() == 0 && !loading)
            new CenterTextModel_()
                    .text("No items")
                    .id("no items model")
                    .addTo(this);
        else
            for (ArtistRelease artistRelease : items)
                new CardListItemModel_()
                        .id("item" + items.indexOf(artistRelease))
                        .imageUrl(artistRelease.getThumb())
                        .context(context)
                        .title(artistRelease.getTitle())
                        .onClick(v -> view.launchDetailedActivity(artistRelease.getType(), artistRelease.getTitle(), artistRelease.getId()))
                        .subtitle(artistRelease.getArtist())
                        .addTo(this);

        new SmallEmptySpaceModel_()
                .id("empty space model bottom")
                .addTo(this);
    }

    public void setItems(List<ArtistRelease> items)
    {
        this.items = items;
        this.loading = false;
        this.error = false;
        requestModelBuild();
    }

    public void setError(String errorMessage)
    {
        this.error = true;
        this.errorMessage = errorMessage;
        this.loading = false;
        requestModelBuild();
    }
}