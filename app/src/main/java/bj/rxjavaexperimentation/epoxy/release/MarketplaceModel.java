package bj.rxjavaexperimentation.epoxy.release;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.joanzapata.iconify.widget.IconTextView;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 24/04/2017.
 */
@EpoxyModelClass(layout = R.layout.item_listing)
public abstract class MarketplaceModel extends EpoxyModel<CardView>
{
    @BindView(R.id.lytMarketplaceItemContainer) ConstraintLayout lytMarketplaceItemContainer;
    @BindView(R.id.tvSleeve) IconTextView tvSleeve;
    @BindView(R.id.tvMedia) IconTextView tvMedia;
    @BindView(R.id.tvShipsFrom) TextView tvShipsFrom;
    @BindView(R.id.tvSeller) TextView tvSeller;
    @BindView(R.id.tvRating) TextView tvRating;
    @BindView(R.id.tvConvertedPrice) TextView tvConvertedPrice;
    @BindView(R.id.tvListedPrice) TextView tvListedPrice;
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @EpoxyAttribute String sleeve;
    @EpoxyAttribute String media;
    @EpoxyAttribute String shipsFrom;
    @EpoxyAttribute String sellerName;
    @EpoxyAttribute String sellerRating;
    @EpoxyAttribute String convertedPrice;
    @EpoxyAttribute String price;

    @Override
    public void bind(CardView view)
    {
        ButterKnife.bind(this, view);
        if (sleeve != null)
            tvSleeve.setText("{fa-inbox} " + sleeve);
        else
            tvSleeve.setText("{fa-inbox} Not graded");
        tvMedia.setText("{fa-music} " + media);
        tvShipsFrom.setText(shipsFrom);
        tvSeller.setText(sellerName);
        tvRating.setText(sellerRating);
        tvConvertedPrice.setText(convertedPrice);
        tvListedPrice.setText(price);
        lytMarketplaceItemContainer.setOnClickListener(onClickListener);
    }
}
