package bj.rxjavaexperimentation.search.epoxy;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
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

@EpoxyModelClass(layout = R.layout.item_discogs_result)
public abstract class SearchResultModel extends EpoxyModel<CardView>
{
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvType) TextView tvType;
    @BindView(R.id.ivImage) ImageView ivImage;
    @EpoxyAttribute String title;
    @EpoxyAttribute String subtitle;
    @EpoxyAttribute String image;
    @EpoxyAttribute(hash = false) View.OnClickListener clickListener;
    private Context context;

    public SearchResultModel(Context context)
    {
        this.context = context;
    }

    @Override
    public void bind(CardView view)
    {
        ButterKnife.bind(this, view);
        tvTitle.setText(title);
        tvType.setText(subtitle);
        Glide.with(context)
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.ic_vinyl)
                .crossFade()
                .into(ivImage);
        view.setOnClickListener(clickListener);
    }

    @Override
    public void unbind(CardView view)
    {
        super.unbind(view);
    }

    public ImageView ivImage()
    {
        return ivImage;
    }
}
