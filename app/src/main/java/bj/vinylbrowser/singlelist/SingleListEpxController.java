package bj.vinylbrowser.singlelist;

import android.content.Context;

import bj.vinylbrowser.common.BaseSingleListController;
import bj.vinylbrowser.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 08/05/2017.
 */
public class SingleListEpxController extends BaseSingleListController
{
    public SingleListEpxController(Context context, SingleListContract.View view, ImageViewAnimator imageViewAnimator)
    {
        this.context = context;
        this.view = view;
        this.imageViewAnimator = imageViewAnimator;
    }
}
