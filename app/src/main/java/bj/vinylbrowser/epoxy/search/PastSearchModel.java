package bj.vinylbrowser.epoxy.search;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.vinylbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 25/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_past_search)
public abstract class PastSearchModel extends EpoxyModel<CardView>
{
    @BindView(R.id.lytSearchTerm) CardView lytSearchTerm;
    @BindView(R.id.tvSearchTerm) TextView tvSearchTerm;
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @EpoxyAttribute String searchTerm;

    @Override
    public void bind(CardView view)
    {
        ButterKnife.bind(this, view);
        tvSearchTerm.setText(searchTerm);
        lytSearchTerm.setOnClickListener(onClickListener);
    }
}
