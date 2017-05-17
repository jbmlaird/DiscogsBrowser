package bj.discogsbrowser.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.SimpleEpoxyController;

import java.util.List;

/**
 * Lifted straight from the Epoxy sample app: https://github.com/airbnb/epoxy/blob/master/epoxy-sample/src/main/java/com/airbnb/epoxy/sample/views/Carousel.java
 * <p>
 * This is a vertical RecyclerView with custom options.
 */
public class Carousel extends RecyclerView
{
    private static final int SPAN_COUNT = 2;

    private SimpleEpoxyController controller;
    private final LinearLayoutManager layoutManager;

    public Carousel(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        // Carousels are generally fixed height. Using fixed size is a small optimization we can make
        // in that case. This isn't safe to do if the models set in this carousel have varying heights.
        setHasFixedSize(false);

        // For the example app we use a grid, but in many real world scenarios a simple
        // linearlayoutmanager is common. You could modify this carousel code to programmatically
        // set the layoutmanager depending on your needs, or hardcode it to a horizontal
        // linearlayoutmanager.
        layoutManager =
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(layoutManager);
    }

    public void setInitialPrefetchItemCount(int count)
    {
        layoutManager.setInitialPrefetchItemCount(count);
    }

    public void setModels(List<? extends EpoxyModel<?>> models)
    {
        // If this is the first time setting models we create a new controller. This is because the
        // first time a controller builds models it happens right away, otherwise it is posted. We
        // need it to happen right away so the models show immediately and so the adapter is
        // populated so the carousel scroll state can be restored.
        if (controller == null)
        {
            controller = new SimpleEpoxyController();
            controller.setSpanCount(SPAN_COUNT);
            setAdapter(controller.getAdapter());
        }

        // If the models are set again without being cleared first (eg colors are inserted, shuffled,
        // or changed), then reusing the same controller allows diffing to work correctly.
        controller.setModels(models);
    }

    public void clearModels()
    {
        // The controller is cleared so the next time models are set we can create a fresh one.
        controller.cancelPendingModelBuild();
        controller = null;

        // We use swapAdapter instead of setAdapter so that the view pool is not cleared.
        // 'removeAndRecycleExistingViews=true' is used
        // since the carousel is going off screen and these views can now be recycled to be used in
        // another carousel (assuming there is a shared view pool)
        swapAdapter(null, true);
    }

    /**
     * Pulled from http://stackoverflow.com/questions/36286391/nested-recyclerview-how-to-prevent-parent-recyclerview-from-getting-scrolled-wh
     * <p>
     * Ensures that if the user scrolls sideways on a nested RecyclerView, it won't get intercepted by the parent RecyclerView.
     *
     * @param event On RecyclerView touch.
     * @return https://www.residentadvisor.net/dj/callsuper
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP)
        {
            // ensure we release the disallow request when the finger is lifted
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        else
        {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);
    }
}