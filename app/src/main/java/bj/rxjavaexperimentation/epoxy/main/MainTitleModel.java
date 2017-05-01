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

@EpoxyModelClass(layout = R.layout.model_main_title)
public abstract class MainTitleModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @EpoxyAttribute String title;
    @EpoxyAttribute Integer size;
    @BindView(R.id.tvSeeAll) TextView tvSeeAll;
    @BindView(R.id.tvHeader) TextView tvHeader;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvHeader.setText(title);
        if (size == 0)
            tvSeeAll.setVisibility(View.GONE);
        else
        {
            tvSeeAll.setVisibility(View.VISIBLE);
            tvSeeAll.setOnClickListener(onClickListener);
        }
    }
}
