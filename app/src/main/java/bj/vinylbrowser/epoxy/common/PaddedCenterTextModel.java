package bj.vinylbrowser.epoxy.common;

import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.vinylbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Josh Laird on 24/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_no_listings)
public abstract class PaddedCenterTextModel extends EpoxyModel<ConstraintLayout>
{
    private Unbinder unbinder;
    @EpoxyAttribute String text;
    @BindView(R.id.textView) TextView textView;

    @Override
    public void bind(ConstraintLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        textView.setText(text);
    }

    @Override
    public void unbind(ConstraintLayout view)
    {
        super.unbind(view);
        unbinder.unbind();
    }
}
