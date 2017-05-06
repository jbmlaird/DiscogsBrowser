package bj.discogsbrowser.epoxy.main;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.discogsbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 17/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_no_orders)
public abstract class NoOrderModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.textView) TextView textView;
    @EpoxyAttribute String text;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        textView.setText(text);
    }
}
