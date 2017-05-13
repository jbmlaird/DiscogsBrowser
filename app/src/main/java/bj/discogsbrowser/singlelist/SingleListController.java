package bj.discogsbrowser.singlelist;

import android.content.Context;

import bj.discogsbrowser.common.BaseSingleListController;
import bj.discogsbrowser.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 08/05/2017.
 */
public class SingleListController extends BaseSingleListController
{
    public SingleListController(Context context, SingleListContract.View view, ImageViewAnimator imageViewAnimator)
    {
        this.context = context;
        this.view = view;
        this.imageViewAnimator = imageViewAnimator;
    }
}
