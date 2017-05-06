package bj.discogsbrowser.epoxy.main;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.bumptech.glide.Glide;

import bj.discogsbrowser.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 20/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_main_user)
public abstract class MainUserModel extends EpoxyModel<LinearLayout>
{
    @EpoxyAttribute String username;
    @EpoxyAttribute String avatarUrl;
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.ivProfilePic) ImageView ivProfilePic;
    private Context context;

    public MainUserModel(Context context)
    {
        this.context = context;
    }

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        tvUsername.setText(username);
        Glide.with(context)
                .load(avatarUrl)
                .into(ivProfilePic);
    }
}
