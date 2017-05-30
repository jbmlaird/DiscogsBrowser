package bj.vinylbrowser.artistreleases;

import android.content.Context;

import java.util.List;

import bj.vinylbrowser.common.BaseSingleListController;
import bj.vinylbrowser.model.common.RecyclerViewModel;
import bj.vinylbrowser.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 08/05/2017.
 */
public class ArtistReleasesEpxController extends BaseSingleListController
{
    public ArtistReleasesEpxController(Context context, ArtistReleasesContract.View view, ImageViewAnimator imageViewAnimator)
    {
        this.context = context;
        this.view = view;
        this.imageViewAnimator = imageViewAnimator;
    }
}