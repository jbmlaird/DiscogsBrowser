package bj.rxjavaexperimentation.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Josh Laird on 08/04/2017.
 */

public class SquareLayout extends LinearLayout
{
    public SquareLayout(Context context)
    {
        super(context);
    }

    public SquareLayout(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
