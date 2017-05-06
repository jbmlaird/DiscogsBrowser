package bj.discogsbrowser.epoxy.common;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.discogsbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 27/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_error)
public abstract class ErrorModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClick;
    @EpoxyAttribute String errorString;
    @BindView(R.id.lytError) LinearLayout lytError;
    @BindView(R.id.tvError) TextView tvError;
    private Unbinder unbinder;

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        tvError.setText(errorString);
        lytError.setOnClickListener(onClick);
    }

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
        unbinder.unbind();
    }
}
