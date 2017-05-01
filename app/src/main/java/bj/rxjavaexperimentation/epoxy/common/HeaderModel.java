package bj.rxjavaexperimentation.epoxy.common;

import android.content.Context;
import android.view.View;
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

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 07/04/2017.
 */

@EpoxyModelClass(layout = R.layout.model_detailed_header)
public abstract class HeaderModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvSubtitle) TextView tvSubtitle;
    @EpoxyAttribute(DoNotHash) Context context;
    @EpoxyAttribute String title;
    @EpoxyAttribute String subtitle;
    @EpoxyAttribute String imageUrl;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvTitle.setText(title);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_vinyl)
                .crossFade()
                .into(ivImage);
        if (!subtitle.equals(""))
        {
            // Controller reuses the same model so visibility has to be restored
            tvSubtitle.setText(subtitle);
            tvSubtitle.setVisibility(View.VISIBLE);
        }
        else
            tvSubtitle.setVisibility(View.GONE);
    }
}
