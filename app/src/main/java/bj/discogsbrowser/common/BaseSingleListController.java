package bj.discogsbrowser.common;

import android.content.Context;

import com.airbnb.epoxy.EpoxyController;

import java.util.ArrayList;
import java.util.List;

import bj.discogsbrowser.R;
import bj.discogsbrowser.epoxy.common.CardListItemModel_;
import bj.discogsbrowser.epoxy.common.CenterTextModel_;
import bj.discogsbrowser.epoxy.common.LoadingModel_;
import bj.discogsbrowser.epoxy.common.SmallEmptySpaceModel_;
import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 09/05/2017.
 */

public abstract class BaseSingleListController extends EpoxyController
{
    protected Context context;
    protected SingleListView view;
    protected ImageViewAnimator imageViewAnimator;
    protected List<? extends RecyclerViewModel> items = new ArrayList<>();
    protected boolean loading = true;
    protected boolean error;
    protected String errorMessage = "";

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
                    .text(context.getString(R.string.no_items))
                    .id("no items model")
                    .addTo(this);
        else
            for (RecyclerViewModel recyclerViewModel : items)
                new CardListItemModel_()
                        .id("item" + items.indexOf(recyclerViewModel))
                        .imageUrl(recyclerViewModel.getThumb())
                        .context(context)
                        .title(recyclerViewModel.getTitle())
                        .onClick(v -> view.launchDetailedActivity(recyclerViewModel.getType(), recyclerViewModel.getTitle(), recyclerViewModel.getId()))
                        .subtitle(recyclerViewModel.getSubtitle())
                        .addTo(this);

        new SmallEmptySpaceModel_()
                .id("empty space model bottom")
                .addTo(this);
    }

    public void setItems(List<? extends RecyclerViewModel> items)
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
