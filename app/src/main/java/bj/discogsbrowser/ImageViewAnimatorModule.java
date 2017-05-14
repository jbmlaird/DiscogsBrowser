package bj.discogsbrowser;

import bj.discogsbrowser.utils.ImageViewAnimator;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 14/05/2017.
 * <p>
 * Espresso tests will fail if there is animation. This will override ImageViewAnimator and disable the rotation.
 */
@Module
class ImageViewAnimatorModule
{
    @Provides
    protected ImageViewAnimator provideImageViewAnimator()
    {
        return new ImageViewAnimator();
    }
}
