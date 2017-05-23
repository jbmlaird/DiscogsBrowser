package bj.vinylbrowser.epoxy.order;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import java.text.NumberFormat;

import bj.vinylbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_order_item)
public abstract class OrderReleaseModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.tvReleaseName) TextView tvReleaseName;
    @BindView(R.id.tvPrice) TextView tvPrice;
    @EpoxyAttribute String releaseName;
    @EpoxyAttribute Double price;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        tvPrice.setText(numberFormat.format(price));
        tvReleaseName.setText(releaseName);
    }
}
