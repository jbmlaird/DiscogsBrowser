package bj.discogsbrowser.epoxy.release;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.discogsbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 24/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_release_marketplace_header)
public abstract class MarketplaceListingsHeader extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute String lowestPrice;
    @EpoxyAttribute String numForSale;
    @BindView(R.id.tvHeader) TextView tvHeader;
    @BindView(R.id.tvMarketplaceSummary) TextView tvMarketplaceSummary;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        if (numForSale.equals("0"))
        {
            tvHeader.setText("Listings");
            tvMarketplaceSummary.setText("");
        }
        else
            tvMarketplaceSummary.setText(numForSale + " from " + lowestPrice);
    }
}
