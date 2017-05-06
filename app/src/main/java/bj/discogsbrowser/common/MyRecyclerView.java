package bj.discogsbrowser.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Josh Laird on 17/04/2017.
 * <p>
 * https://github.com/airbnb/epoxy/wiki/Avoiding-Memory-Leaks
 */
public class MyRecyclerView extends RecyclerView
{
    public MyRecyclerView(Context context)
    {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        setAdapter(null);
        // Or use swapAdapter(null, true) so that the existing views are recycled to the view pool
    }
}