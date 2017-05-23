package bj.vinylbrowser.epoxy.common;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import bj.vinylbrowser.R;
import bj.vinylbrowser.utils.ImageViewAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Josh Laird on 17/04/2017.
 */
@EpoxyModelClass(layout = R.layout.model_loading)
public abstract class LoadingModel extends EpoxyModel<LinearLayout>
{
    @BindView(R.id.ivLoading) ImageView ivLoading;
    @EpoxyAttribute ImageViewAnimator imageViewAnimator;

    @Override
    public void bind(LinearLayout view)
    {
        ButterKnife.bind(this, view);
        imageViewAnimator.rotateImage(ivLoading);
    }
}
