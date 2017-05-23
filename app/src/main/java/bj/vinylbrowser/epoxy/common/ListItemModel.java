package bj.vinylbrowser.epoxy.common;

import android.content.Context;
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

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_label_release)
public abstract class ListItemModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.lytLabelReleaseContainer) LinearLayout lytLabelReleaseContainer;
    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvArtists) TextView tvArtists;
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClick;
    @EpoxyAttribute(DoNotHash) Context context;
    @EpoxyAttribute String imageUrl;
    @EpoxyAttribute String title;
    @EpoxyAttribute String subtitle;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        lytLabelReleaseContainer.setOnClickListener(onClick);
        tvTitle.setText(title);
        tvArtists.setText(subtitle);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_vinyl)
                .crossFade()
                .into(ivImage);
    }
}
