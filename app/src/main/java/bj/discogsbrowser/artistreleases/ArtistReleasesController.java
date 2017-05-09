package bj.discogsbrowser.artistreleases;

import android.content.Context;

import javax.inject.Inject;

import bj.discogsbrowser.common.BaseSingleListController;
import bj.discogsbrowser.utils.ImageViewAnimator;

/**
 * Created by Josh Laird on 08/05/2017.
 */
public class ArtistReleasesController extends BaseSingleListController
{
    @Inject
    public ArtistReleasesController(Context context, ArtistReleasesContract.View view, ImageViewAnimator imageViewAnimator)
    {
        this.context = context;
        this.view = view;
        this.imageViewAnimator = imageViewAnimator;
    }
}