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
@EpoxyModelClass(layout = R.layout.model_error)
public abstract class ErrorModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute String errorString;
    @BindView(R.id.tvError) TextView tvError;
    private Unbinder unbinder;

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        tvError.setText(errorString);
    }

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
        unbinder.unbind();
    }
}
