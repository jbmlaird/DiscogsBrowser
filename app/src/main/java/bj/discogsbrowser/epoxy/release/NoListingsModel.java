package bj.discogsbrowser.epoxy.release;

import android.support.constraint.ConstraintLayout;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.discogsbrowser.R;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 24/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_no_listings)
public abstract class NoListingsModel extends EpoxyModel<ConstraintLayout>
{
    @Override
    public void bind(ConstraintLayout view)
    {
        ButterKnife.bind(this, view);
    }
}
