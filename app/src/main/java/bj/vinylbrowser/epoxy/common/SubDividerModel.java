package bj.vinylbrowser.epoxy.common;

import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.vinylbrowser.R;

/**
 * Created by Josh Laird on 25/04/2017.
 */
@EpoxyModelClass(layout = R.layout.divider_marketplace)
public abstract class SubDividerModel extends EpoxyModel<LinearLayout>
{
    @Override
    public void bind(LinearLayout view)
    {
        super.bind(view);
    }
}
