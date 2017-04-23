package bj.rxjavaexperimentation.artist.epoxy;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 21/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_subheader)
public abstract class SubHeaderModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.tvSubheader) TextView tvSubheader;
    @EpoxyAttribute String subheader;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvSubheader.setText(subheader);
    }
}
