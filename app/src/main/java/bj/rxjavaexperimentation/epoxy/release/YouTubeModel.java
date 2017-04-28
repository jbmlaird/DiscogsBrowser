package bj.rxjavaexperimentation.epoxy.release;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import bj.rxjavaexperimentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * Created by Josh Laird on 28/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_youtube)
public abstract class YouTubeModel extends EpoxyModel<LinearLayout> implements YouTubeThumbnailView.OnInitializedListener
{
    @EpoxyAttribute(DoNotHash) View.OnClickListener onClick;
    @EpoxyAttribute String youTubeKey;
    @EpoxyAttribute String youTubeId;
    @EpoxyAttribute String title;
    @EpoxyAttribute String description;
    @BindView(R.id.lytYoutube) LinearLayout lytYoutube;
    @BindView(R.id.ivImage) YouTubeThumbnailView ivImage;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvDescription) TextView tvDescription;
    private Unbinder unbinder;
    private YouTubeThumbnailLoader loader;

    @Override
    public void bind(LinearLayout view)
    {
        unbinder = ButterKnife.bind(this, view);
        lytYoutube.setOnClickListener(onClick);
        tvTitle.setText(title);
        tvDescription.setText(description);
        ivImage.initialize(youTubeKey, this);
    }

    @Override
    public void unbind(LinearLayout view)
    {
        super.unbind(view);
        unbinder.unbind();
        loader.release();
    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader)
    {
        youTubeThumbnailLoader.setVideo(youTubeId);
        loader = youTubeThumbnailLoader;
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult)
    {

    }
}
