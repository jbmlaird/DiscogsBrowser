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

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_view_on_discogs)
public abstract class WholeLineModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @EpoxyAttribute String text;
    @BindView(R.id.lytViewOnDiscogs) LinearLayout lytViewOnDiscogs;
    @BindView(R.id.tvText) TextView tvText;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvText.setText(text);
        lytViewOnDiscogs.setOnClickListener(onClickListener);
    }
}
