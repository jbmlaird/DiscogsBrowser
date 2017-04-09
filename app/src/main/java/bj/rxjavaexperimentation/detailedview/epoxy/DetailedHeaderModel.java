package bj.rxjavaexperimentation.detailedview.epoxy;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.bumptech.glide.Glide;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 07/04/2017.
 */

@EpoxyModelClass(layout = R.layout.model_detailed_top)
public abstract class DetailedHeaderModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvSubtitle) TextView tvSubtitle;
    @EpoxyAttribute String title;
    @EpoxyAttribute String subtitle;
    @EpoxyAttribute String imageUrl;
    private Context context;

    public DetailedHeaderModel(Context context)
    {
        this.context = context;
    }

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvTitle.setText(title);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .crossFade()
                .into(ivImage);
        tvSubtitle.setText(subtitle);
    }
}
