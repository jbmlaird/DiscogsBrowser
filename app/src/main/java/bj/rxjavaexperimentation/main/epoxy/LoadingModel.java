package bj.rxjavaexperimentation.main.epoxy;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.rxjavaexperimentation.R;
import bj.rxjavaexperimentation.utils.ImageViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 17/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_loading)
public abstract class LoadingModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.ivLoading) ImageView ivLoading;
    private ImageViewAnimator imageViewAnimator;

    public LoadingModel(ImageViewAnimator imageViewAnimator)
    {
        this.imageViewAnimator = imageViewAnimator;
    }

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        imageViewAnimator.rotateImage(ivLoading);
    }
}
