package bj.rxjavaexperimentation.order.epoxy;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_order_buyer)
public abstract class BuyerModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute String status;
    @EpoxyAttribute String buyer;
    @EpoxyAttribute String specialInstructions;
    @EpoxyAttribute String shippingAddress;
    @BindView(R.id.tvStatus) TextView tvStatus;
    @BindView(R.id.tvBuyer) TextView tvBuyer;
    @BindView(R.id.tvSpecialInstructions) TextView tvSpecialInstructions;
    @BindView(R.id.tvShippingAddress) TextView tvShippingAddress;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvStatus.setText(status);
        tvBuyer.setText("Buyer: " + buyer);
        tvSpecialInstructions.setText(specialInstructions);
        tvShippingAddress.setText(shippingAddress);
    }
}
