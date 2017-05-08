package bj.discogsbrowser.epoxy.common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import bj.discogsbrowser.R;
import bj.discogsbrowser.utils.ImageViewAnimator;
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
    @EpoxyAttribute ImageViewAnimator imageViewAnimator;
    @EpoxyAttribute String title;
    @EpoxyAttribute String subtitle;
    @EpoxyAttribute String imageUrl;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvTitle.setText(title);
        if (imageViewAnimator != null && imageUrl != null && !imageUrl.equals(""))
            imageViewAnimator.rotateImage(ivImage);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_vinyl)
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>()
                {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource)
                    {
                        ivImage.clearAnimation();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource)
                    {
                        ivImage.clearAnimation();
                        return false;
                    }
                })
                .into(ivImage);
        if (subtitle != null && !subtitle.equals(""))
        {
            tvSubtitle.setText(subtitle);
            tvSubtitle.setVisibility(View.VISIBLE);
        }
        else
            tvSubtitle.setVisibility(View.GONE);
    }
}
