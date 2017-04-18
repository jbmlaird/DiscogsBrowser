package bj.rxjavaexperimentation.main.epoxy;

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
 * Created by Josh Laird on 18/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_view_more)
public abstract class ViewMoreModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute String title;
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClickListener;
    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.lytViewMore) LinearLayout lytViewMore;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        textView.setText(title);
        lytViewMore.setOnClickListener(onClickListener);
    }
}
