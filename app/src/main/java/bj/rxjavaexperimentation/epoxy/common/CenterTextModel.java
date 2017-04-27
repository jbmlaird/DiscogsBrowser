package bj.rxjavaexperimentation.epoxy.common;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Josh Laird on 27/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_center_text)
public abstract class CenterTextModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.tvMessage) TextView tvMessage;
    @EpoxyAttribute String text;
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
        tvMessage.setText(text);
    }
}
