package bj.rxjavaexperimentation.epoxy.main;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 17/04/2017.
 */

@EpoxyModelClass(layout = R.layout.model_main_header)
public abstract class MainHeaderModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @EpoxyAttribute String title;
    @BindView(R.id.tvSeeAll) TextView tvSeeAll;
    @BindView(R.id.tvHeader) TextView tvHeader;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvSeeAll.setOnClickListener(onClickListener);
        tvHeader.setText(title);
    }
}
