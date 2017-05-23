package bj.vinylbrowser.epoxy.main;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.bumptech.glide.Glide;

import bj.vinylbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Josh Laird on 05/05/2017.
 */
@EpoxyModelClass(layout = R.layout.model_carousel_item)
public abstract class ViewedReleaseModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.lytViewedRelease) LinearLayout lytViewedRelease;
    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.tvReleaseName) TextView tvReleaseName;
    @EpoxyAttribute(hash = false) View.OnClickListener onClickListener;
    @EpoxyAttribute Context context;
    @EpoxyAttribute String releaseName;
    @EpoxyAttribute String thumbUrl;

    private Unbinder unbinder;

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        tvReleaseName.setText(releaseName);
        Glide.with(context)
                .load(thumbUrl)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_vinyl))
                .into(ivImage);
        lytViewedRelease.setOnClickListener(onClickListener);
    }

    @Override
    public void unbind(LinearLayout view)
    {
        unbinder.unbind();
        super.unbind(view);
    }
}
