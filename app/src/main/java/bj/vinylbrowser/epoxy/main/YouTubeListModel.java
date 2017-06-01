package bj.vinylbrowser.epoxy.main;

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
import butterknife.Unbinder;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 01/06/2017.
 */
@EpoxyModelClass(layout = R.layout.model_youtube_list)
public abstract class YouTubeListModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute(DoNotHash) Context context;
    @EpoxyAttribute(DoNotHash) View.OnClickListener onRemoveClick;
    @EpoxyAttribute(DoNotHash) View.OnClickListener onPlayClick;
    @EpoxyAttribute String imageUrl;
    @EpoxyAttribute String title;
    @EpoxyAttribute String description;
    @BindView(R.id.lytText) LinearLayout lytText;
    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.ivRemove) ImageView ivRemove;
    private Unbinder unbinder;

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        lytText.setOnClickListener(onPlayClick);
        ivRemove.setOnClickListener(onRemoveClick);
        tvTitle.setText(title);
        tvDescription.setText(description);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_vinyl)
                .crossFade()
                .into(ivImage);
    }

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
        unbinder.unbind();
    }
}
