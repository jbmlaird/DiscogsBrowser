package bj.discogsbrowser.singlelist;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.epoxy.common.CardListItemModel_;
import bj.discogsbrowser.epoxy.common.CenterTextModel_;
import bj.discogsbrowser.epoxy.common.LoadingModel_;
import bj.discogsbrowser.epoxy.common.SmallEmptySpaceModel_;
import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 08/05/2017.
 */
public class SingleListController extends EpoxyController
{
    private Context context;
    private SingleListContract.View view;
    private ImageViewAnimator imageViewAnimator;
    private List<? extends RecyclerViewModel> items = new ArrayList<>();
    private boolean loading = true;
    private boolean error;
    private String errorMessage = "";

    @Inject
    public SingleListController(Context context, SingleListContract.View view, ImageViewAnimator imageViewAnimator)
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
                    .text(errorMessage)
                    .addTo(this);
        else if (items.size() == 0 && !loading)
            new CenterTextModel_()
                    .text("No items")
                    .id("no items model")
                    .addTo(this);
        else
            for (RecyclerViewModel item : items)
                new CardListItemModel_()
                        .id("item" + items.indexOf(item))
                        .imageUrl(item.getThumb())
                        .context(context)
                        .title(item.getTitle())
                        .onClick(v -> view.launchDetailedActivity(item.getType(), item.getTitle(), item.getId()))
                        .subtitle(item.getSubtitle())
                        .addTo(this);

        new SmallEmptySpaceModel_()
                .id("empty space model bottom")
                .addTo(this);
    }

    public void setItems(List<? extends RecyclerViewModel> items)
    {
        this.items = items;
        loading = false;
        error = false;
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
