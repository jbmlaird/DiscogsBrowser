package bj.rxjavaexperimentation.main.epoxy;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.utils.DateFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 17/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_order)
public abstract class OrderModel extends EpoxyModel<ConstraintLayout>
{
    private DateFormatter dateFormatter;
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @EpoxyAttribute String buyer;
    @EpoxyAttribute String lastActivity;
    @EpoxyAttribute String status;
    @BindView(R.id.lytOrder) ConstraintLayout lytOrder;
    @BindView(R.id.tvBuyer) TextView tvBuyer;
    @BindView(R.id.tvLastActivity) TextView tvLastActivity;
    @BindView(R.id.tvStatus) TextView tvStatus;

    public OrderModel(DateFormatter dateFormatter)
    {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public void bind(ConstraintLayout view)
    {
        ButterKnife.bind(this, view);
        tvBuyer.setText("Buyer: " + buyer);
        tvLastActivity.setText(dateFormatter.formatIsoDate(lastActivity));
        tvStatus.setText(status);
        lytOrder.setOnClickListener(onClickListener);
    }
}
