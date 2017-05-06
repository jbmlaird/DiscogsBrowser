package bj.discogsbrowser.epoxy.order;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import java.text.NumberFormat;

import bj.discogsbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 18/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_order_total)
public abstract class TotalModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.tvTotal) TextView tvTotal;
    @EpoxyAttribute Double total;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        tvTotal.setText(numberFormat.format(total));
    }
}
