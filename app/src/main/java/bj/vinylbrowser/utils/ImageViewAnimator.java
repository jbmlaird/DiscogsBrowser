package bj.vinylbrowser.utils;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by Josh Laird on 17/04/2017.
 * <p>
 * Rotates the vinyl image. This UX business ain't so bad?
 */
public class ImageViewAnimator
{
    public void rotateImage(ImageView imageView)
    {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        imageView.startAnimation(rotateAnimation);
    }
}
