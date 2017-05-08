package bj.discogsbrowser.epoxy.common;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.bumptech.glide.Glide;

import bj.discogsbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_card_list_item)
public abstract class CardListItemModel extends EpoxyModel<CardView>
{
    @BindView(R.id.lytLabelReleaseContainer) CardView lytListItemContainer;
    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvArtists) TextView tvSubtitle;
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClick;
    @EpoxyAttribute(DoNotHash) Context context;
    @EpoxyAttribute String imageUrl;
    @EpoxyAttribute String title;
    @EpoxyAttribute String subtitle;
    private Unbinder unbinder;

    @Override
    public void bind(CardView view)
    {
        unbinder = ButterKnife.bind(this, view);
        lytListItemContainer.setOnClickListener(onClick);
        tvTitle.setText(title);
        tvSubtitle.setText(subtitle);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_vinyl)
                .crossFade()
                .into(ivImage);
    }

    @Override
    public void unbind(CardView view)
    {
        super.unbind(view);
        unbinder.unbind();
    }
}
