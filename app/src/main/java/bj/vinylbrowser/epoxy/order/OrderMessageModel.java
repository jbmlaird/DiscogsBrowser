package bj.vinylbrowser.epoxy.order;

import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.vinylbrowser.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Josh Laird on 01/05/2017.
 */
@EpoxyModelClass(layout = R.layout.model_order_message)
public abstract class OrderMessageModel extends EpoxyModel<LinearLayout>
{
    private Unbinder unbinder;

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
        unbinder.unbind();
    }

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
    }
}
