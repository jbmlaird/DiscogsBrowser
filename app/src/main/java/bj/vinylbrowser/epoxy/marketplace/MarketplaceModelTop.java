package bj.vinylbrowser.epoxy.marketplace;

import android.content.Context;
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

import bj.vinylbrowser.R;
import bj.vinylbrowser.utils.ImageViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Josh Laird on 09/05/2017.
 */
@EpoxyModelClass(layout = R.layout.model_marketplace_top)
public abstract class MarketplaceModelTop extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.tvItemName) TextView tvItemName;
    @BindView(R.id.tvPrice) TextView tvPrice;
    @BindView(R.id.ivImage) ImageView ivImage;
    @EpoxyAttribute ImageViewAnimator imageViewAnimator;
    @EpoxyAttribute Context context;
    @EpoxyAttribute String itemName;
    @EpoxyAttribute String thumbUrl;
    @EpoxyAttribute String price;
    private Unbinder unbinder;

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        tvPrice.setText(price);
        tvItemName.setText(itemName);
        if (thumbUrl != null && !thumbUrl.equals(""))
            imageViewAnimator.rotateImage(ivImage);
        Glide.with(context)
                .load(thumbUrl) //listing.getRelease().getThumbnail()
                .dontAnimate()
                .placeholder(R.drawable.ic_vinyl)
                .listener(new RequestListener<String, GlideDrawable>()
                {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource)
                    {
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
    }

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
        unbinder.unbind();
    }
}
