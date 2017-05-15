package bj.discogsbrowser.epoxy.marketplace;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.discogsbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Josh Laird on 09/05/2017.
 */
@EpoxyModelClass(layout = R.layout.model_marketplace_center)
public abstract class MarketplaceModelCenter extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute String sleeveCondition = "n/a";
    @EpoxyAttribute String mediaCondition;
    @EpoxyAttribute String sellerUsername;
    @EpoxyAttribute Double sellerRating = 0.0;
    @EpoxyAttribute String comments;
    @BindView(R.id.tvSeller) TextView tvSeller;
    @BindView(R.id.tvSellerRating) TextView tvSellerRating;
    @BindView(R.id.tvSleeve) TextView tvSleeve;
    @BindView(R.id.tvMedia) TextView tvMedia;
    @BindView(R.id.tvComments) TextView tvComments;
    private Unbinder unbinder;

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        tvSleeve.setText(sleeveCondition);
        tvMedia.setText(mediaCondition);
        tvSeller.setText(sellerUsername);
        tvSellerRating.setText(sellerRating + "%");
        if (comments != null && !comments.equals(""))
            tvComments.setText(comments);
    }

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
        unbinder.unbind();
    }
}
