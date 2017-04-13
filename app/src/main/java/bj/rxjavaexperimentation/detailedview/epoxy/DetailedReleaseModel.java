package bj.rxjavaexperimentation.detailedview.epoxy;

import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import java.util.ArrayList;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.model.listing.MyListing;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 12/04/2017.
 */

@EpoxyModelClass(layout = R.layout.model_detailed_release)
public abstract class DetailedReleaseModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute ArrayList<MyListing> myListings = new ArrayList<>();

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
    }

    private class ListingViewHolder
    {
        public ListingViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
