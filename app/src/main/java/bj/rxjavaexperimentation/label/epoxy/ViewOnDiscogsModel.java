package bj.rxjavaexperimentation.label.epoxy;

import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_view_on_discogs)
public abstract class ViewOnDiscogsModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @BindView(R.id.lytViewOnDiscogs) LinearLayout lytViewOnDiscogs;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        lytViewOnDiscogs.setOnClickListener(onClickListener);
    }
}
